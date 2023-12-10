import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Klient");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);

        // Rozpocznij wątek do odbierania wiadomości od serwera.
        new Thread(new FutureTask<>(new MessageReceiver())).start();
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);
/*
        JLabel messageLabel = new JLabel("Wiadomość:");
        messageLabel.setBounds(10, 20, 80, 25);
        panel.add(messageLabel);

        JTextField messageText = new JTextField(20);
        messageText.setBounds(100, 20, 165, 25);
        panel.add(messageText);

        JButton sendButton = new JButton("Wyślij");
        sendButton.setBounds(10, 60, 80, 25);
        panel.add(sendButton);
*/
        JTextField usernameText = new JTextField(20);
        usernameText.setBounds(10, 20, 80, 25);
        panel.add(usernameText);

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 20, 165, 25);
        panel.add(passwordField);

        JButton loginButton = new JButton("Zaloguj się");
        loginButton.setBounds(10, 60, 80, 25);
        panel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameText.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars); // Konwersja hasła z char[] na String

                // Wysyłanie danych do serwera w celu uwierzytelnienia
                sendLoginDataToServer(username, password);
            }
        });
    }

    private static void sendLoginDataToServer(String username, String password) {
        try {
            Socket socket = new Socket("localhost", 12345);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            // Wysłanie danych do serwera w określonym formacie, na przykład:
            writer.println("LOGIN|" + username + "|" + password);

            writer.close();
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private static class MessageReceiver implements Callable<Void> {
        @Override
        public Void call() {
            try {
                Socket socket = new Socket("localhost", 12345);
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message;
                while ((message = reader.readLine()) != null) {
                    displayMessageInGUI(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private void displayMessageInGUI(String message) {
            System.out.println(message);
        }
    }
}
