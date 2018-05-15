import  java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Client extends JFrame{
    private JTextField userText;
    private JTextArea chatWindow;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String message = "";
    private String serverIP;
    private Socket connection;

    //constructor
    public Client(String host){
        super("Client mofo!");
        serverIP = host;
        userText = new JTextField();
        userText.setEditable(false);
        userText.addActionListener(
                new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        sendData(actionEvent.getActionCommand());
                        userText.setText("");
                    }
                }
        );
        add(userText, BorderLayout.NORTH);
        chatWindow = new JTextArea();
        add(new JScrollPane(chatWindow), BorderLayout.CENTER);
        setSize(300,150);
        setVisible(true);
    }

    //connect to server
    public void startRunning(){
        try {
            connectToServer();
            setupStreams();
            whileChatting();
        }catch (EOFException eofException){
            showMessage("\n Client terminated connection");
        }catch (IOException ioException){
            ioException.printStackTrace();
        }finally {
            closeCrap();
        }
    }


    //connect to server
    private void connectToServer() throws IOException{
        showMessage("Attempting connection... \n");
        connection = new Socket(InetAddress.getByName(serverIP), 6789);
        showMessage("Connect to: " + connection.getInetAddress().getHostName());
    }


    //set up streamsFirsh unata kara ithin
    private void setupStreams() throws IOException{
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connection.getInputStream());
        showMesssage("\n dude your streams are now good to go");
    }
}
