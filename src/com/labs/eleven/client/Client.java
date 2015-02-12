package com.labs.eleven.client;

import com.labs.common.Logger;
import org.apache.commons.io.IOUtils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

/**
 * Adrian Cooney (12394581)
 * 07/02/15 com.labs.eleven
 */
public class Client extends JFrame {
    // GUI control
    Dimension screenSize = getToolkit().getScreenSize();

    // Socket control
    private int port;
    private String host;
    private Socket socket;

    // Debug
    private Logger logger = new Logger("Client");

    // UI
    private JButton uploadButton;
    private JButton selectButton;
    private JLabel uploadLabel;
    private JList fileList;
    private JButton downloadButton;
    private JButton refreshButton;
    private JTextArea consoleText;

    // State
    private File currentUploadFile;

    public Client(String host, int port) {
        this.port = port;
        this.host = host;

        setTitle("Sockets");
        setLocation((screenSize.width / 2), (screenSize.height / 2));

        scaffold();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        // Initially refresh the UI
        try {
            refresh();
        } catch (IOException e) {
            error("IO Error!");
        }
    }

    /**
     * Create the UI.
     */
    private void scaffold() {
        JPanel base = new JPanel();
        GridLayout layout = new GridLayout(0, 3);
        base.setLayout(layout);
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel(new BorderLayout());
        JPanel consolePanel = new JPanel(new BorderLayout());
        base.add(leftPanel);
        base.add(rightPanel);
        base.add(consolePanel);

        // Create the select controls
        uploadLabel = new JLabel("No file selected.");
        selectButton = new JButton("Select File");
        uploadButton = new JButton("Upload");
        leftPanel.add(uploadLabel);
        leftPanel.add(selectButton);
        leftPanel.add(uploadButton);

        // Create the download controls
        fileList = new JList(new String[] { "One", "Two", "Three" });
        JPanel downloadButtons = new JPanel();
        downloadButtons.setLayout(new BorderLayout());
        refreshButton = new JButton("Refresh");
        downloadButton = new JButton("Download");
        downloadButtons.add(refreshButton, BorderLayout.WEST);
        downloadButtons.add(downloadButton, BorderLayout.EAST);

        // Console
        consoleText = new JTextArea();
        consoleText.setEnabled(false);
        consoleText.setBackground(Color.black);
        consoleText.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        consoleText.setMargin(new Insets(4, 10, 10, 10));
        DefaultCaret caret = (DefaultCaret) consoleText.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(consoleText);
        consolePanel.add(new JLabel("Request Log"), BorderLayout.NORTH);
        consolePanel.add(scrollPane, BorderLayout.CENTER);

        // Add them
        rightPanel.add(new JLabel("Files"), BorderLayout.NORTH);
        rightPanel.add(fileList, BorderLayout.CENTER);
        rightPanel.add(downloadButtons, BorderLayout.SOUTH);

        base.setPreferredSize(new Dimension(600, 180));

        this.add(base);

        // Add the event handlers
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSelectButton();
            }
        });
        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDownloadButton();
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRefreshButton();
            }
        });
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUploadButton();
            }
        });
    }

    /**
     * Make a request to the server.
     * @param data Data to send to the server.
     * @return
     * @throws IOException
     */
    private InputStream request(String data, InputStream stream) throws IOException {
        Socket req = new Socket(host, port);
        DataOutputStream out = new DataOutputStream(req.getOutputStream());
        out.write(data.getBytes()); // Write the command

        if(stream == null) {
            // If we have no stream, just terminate
            out.write("\4".getBytes());
        } else {
            out.write(" >>>".getBytes()); // Tell the server to expect raw bytes
            IOUtils.copy(stream, out); // Pipe in the stream
            out.close();
            return null;
        }

        return req.getInputStream();
    }

    /**
     * Create a request without an output stream passed along.
     * @param data
     * @return
     * @throws IOException
     */
    private InputStream request(String data) throws IOException {
        return request(data, null);
    }

    /**
     * Send a command to the server and return the server's output as a string.
     * @param command "<command> ...<arg>"
     * @return
     * @throws IOException
     */
    private InputStream commandStream(String command, InputStream stream) throws IOException {
        logger.log("$ %s", command);
        consoleText.append("> " + command + "\n");

        // Make the request to the server, convert the response input stream to a string and print
        return request(command, stream);
    }

    /**
     * Create a command and return the input stream from the request.
     * @param command
     * @return
     * @throws IOException
     */
    private InputStream commandStream(String command) throws IOException {
        return commandStream(command, null);
    }

    /**
     * Create a command and return the response as a string with a file stream.
     * @param command
     * @param stream
     * @return
     * @throws IOException
     */
    private String command(String command, InputStream stream) throws IOException {
        InputStream input = commandStream(command, stream);

        String data = IOUtils.toString(input);

        logger.log(data);
        consoleText.append(data + "\n");

        return data;
    }

    /**
     * Create a command without a stream.
     * @param command
     * @return
     * @throws IOException
     */
    private String command(String command) throws IOException {
        return command(command, null);
    }

    /**
     * On select event listener.
     */
    protected void handleSelectButton() {
        logger.log("Selecting file.");
        try {
            select();
        } catch (IOException e) {
            error("IO Exception");
        }
    }

    /**
     * On upload event listener.
     */
    protected void handleUploadButton() {
        logger.log("Uploading file.");

        try {
            upload();
        } catch (IOException e) {
            error("IO Exception");
        }
    }

    /**
     * On refresh event listener.
     */
    protected void handleRefreshButton() {
        logger.log("Refreshing.");

        try {
            refresh();
        } catch (IOException e) {
            error("IO Exception.");
        }
    }

    /**
     * On download event listener.
     */
    protected void handleDownloadButton() {
        logger.log("Downloading.");

        try {
            String selected = fileList.getSelectedValue().toString();

            if(selected == null) {
                error("No file selected.");
                return;
            }

            download(selected);
        } catch (IOException e) {
            error("IO Exception");
        }
    }

    /**
     * Display an error dialog with a message.
     * @param message
     */
    protected void error(String message) {
        JOptionPane.showMessageDialog(this,
                message,
                "Error",
                JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Refresh the file list.
     * @throws IOException
     */
    private void refresh() throws IOException {
        String files = command("LIST");

        fileList.setListData(files.split("\n"));
    }

    /**
     * Display a file chooser dialog and download the file from the server.
     * @param path
     * @throws IOException
     */
    private void download(String path) throws IOException {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        chooser.setDialogTitle("Select directory to save file");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = new File(path);
            String filename = file.getName();
            String outputPath = chooser.getSelectedFile() + "/" + filename;

            File outputFile = new File(outputPath);
            FileOutputStream fout = new FileOutputStream(outputFile);

            IOUtils.copy(commandStream("DOWNLOAD /" + filename), fout);
        } else {
            System.out.println("No Selection ");
        }
    }

    /**
     * Select a file and set the label to the filename.
     * @throws IOException
     */
    private void select() throws IOException {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Select file to select");
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            currentUploadFile = chooser.getSelectedFile();
            uploadLabel.setText(chooser.getName(currentUploadFile));
        } else {
            System.out.println("No Selection ");
        }
    }

    /**
     * Upload a file to the server.
     * @throws IOException
     */
    private void upload() throws IOException {
        if(currentUploadFile == null) {
            error("No file selected");
            return;
        }

        FileInputStream fin = new FileInputStream(currentUploadFile);

        commandStream("UPLOAD " + currentUploadFile.getName(), fin);

        // Refresh the filelist
        refresh();
    }
}
