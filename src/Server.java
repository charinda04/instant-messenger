import  java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Server extends JFrame{
    private JTextField userText;
    private JTextArea chatWindow;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private ServerSocket server;
    private Socket connection;

    //constructor
    public Server(){
        super("chari's instant messenger");
        userText = new JTextField();
        userText.setEditable(false);
        userText.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        sendMessage(actionEvent.getActionCommand());
                        userText.setText("");
                    }
                }
        );
        add(userText, BorderLayout.NORTH);
        chatWindow = new JTextArea();
        add(new JScrollPane(chatWindow));
        setSize(300,150);
        setVisible(true);
    }

    //setup and run the server
    public void startRunning(){
        try {
            server = new ServerSocket(6789,100);
            while (true){
                try {
                    waitForConnection();
                    setupStreams();
                    whileChatting();
                }catch (EOFException eofException){
                    showMessage("\n Server ended the connection! ");
                }finally{
                    closeCrap();
                }
            }
        }catch (IOException ioException){
            ioException.printStackTrace();
        }
    }

    //wait for connection, then display connection information
    private void waitForConnection() throws IOException{
        showMessage("waiting for someone to connect \n");
        connection = server.accept();
        showMessage("now connected to " + connection.getInetAddress().getHostName());
    }

    //get stream to send and recieve data
    private void setupStreams() throws IOException{
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connection.getInputStream());
        showMessage("\n Streams are now setup! \n");
    }

    //during the chat conversation
    private void whileChatting() throws IOException{
        String message = " You are now connected ";
        sednMessage(message);
        ableToType(true);
        do {
            try {
                message = (String)input.readObject();
                showMessage("\n" + message);

            }catch (ClassNotFoundException classNotFoundException){
                showMessage("\n idk wtf user sent" );
            }

        }while (!message.equals("CLIENT - END"))
    }

}
