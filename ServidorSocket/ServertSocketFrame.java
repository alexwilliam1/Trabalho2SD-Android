import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServertSocketFrame extends JFrame implements ActionListener {
    JButton btnProcess;
    private static JTextArea txtS;
    private static int portaServidor = 3030;
    private static Clip clip;
    private static JScrollPane scroll;

    public ServertSocketFrame() {
        this.setTitle("Servidor");
        this.setSize(320, 240);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        getContentPane().setLayout(null);

        btnProcess = new JButton("Parar Alarme");
        btnProcess.setBounds(90, 40, 120, 21);
        btnProcess.addActionListener(this);
        add(btnProcess);
        
        scroll = new JScrollPane(txtS);

        txtS = new JTextArea();
        txtS.setEditable(false);
        txtS.setBounds(10, 85, 290, 120);
        add(txtS);
        this.add(scroll);
        //this.pack();
        this.setVisible(true);
    }
    
    public static void main(String[] args) throws IOException {
        new ServertSocketFrame();
        ServerSocket ServerSocket = null;
        ServerSocket = new ServerSocket(portaServidor);

        System.out.println("Servidor iniciado!");
        txtS.setText("Servidor incializado!");
        txtS.append("\nOuvindo a porta 3030");
        while (true) {
            Socket socket = ServerSocket.accept();
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            if(br != null){
                String msg = br.readLine();
                txtS.append("\n"+msg);
                System.out.println("Mensagem do cliente: " + msg);
                try {
                    txtS.append("\nALARME ATIVADO!");
                    File soundFile = new File("C:\\Users\\Alex William\\Downloads\\Music\\alarme.wav");
                    AudioInputStream sound = AudioSystem.getAudioInputStream(soundFile);
                    DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
                    clip = (Clip) AudioSystem.getLine(info);
                    clip.open(sound);
                    clip.start();
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                    Thread.sleep(10000);
                } catch (Exception e) {
                    System.out.println("ERRO");
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnProcess)) {
            try {
                processInformation();
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void processInformation() throws UnknownHostException, IOException {
           clip.stop();
           txtS.append("\nALARME PARADO!");
    }
}