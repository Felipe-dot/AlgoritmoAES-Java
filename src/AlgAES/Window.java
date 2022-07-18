package AlgAES;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class Window extends JFrame implements ActionListener{
	
	private String textoPuro;
	private byte[] textoCriptografado;
	private String chaveDeCriptografia;
	static String IV = "AAAAAAAAAAAAAAAA";
	
	public byte[] getTextoCriptografado() {
		return textoCriptografado;
	}
	public void setTextoCriptografado(byte[] textoCriptografado) {
		this.textoCriptografado = textoCriptografado;
	}
	
	public String getTextoPuro() {
		return textoPuro;
	}
	public void setTextoPuro(String textoPuro) {
		this.textoPuro = textoPuro;
	}
	
	public String getChaveDeCriptografia() {
		return chaveDeCriptografia;
	}
	public void setChaveDeCriptografia(String chaveDeCriptografia) {
		this.chaveDeCriptografia = chaveDeCriptografia;
	}
	
	JButton button;
	JTextField textField;
	JLabel label;
	JLabel label2;
	JLabel textoEncriptado;
	JLabel textoDescriptografado;
	
	Window(String chaveDeCriptografia){
		setChaveDeCriptografia(chaveDeCriptografia);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new FlowLayout());
		this.setSize(900,650);
        this.setTitle("Vamos criptografar");
        	
        Border border = BorderFactory.createLineBorder(Color.BLACK, 3);
        
		label = new JLabel();
	    label.setText("Texto Criptografado");
		label.setBorder(border);
	    label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        
	    label2 = new JLabel();
	    label2.setText("Texto Descriptografado");
	    label2.setBorder(border);
	    label2.setVerticalAlignment(JLabel.CENTER);
        label2.setHorizontalAlignment(JLabel.CENTER);
        
	    textoEncriptado = new JLabel();
	    textoEncriptado.setBorder(border);
	    textoEncriptado.setVerticalAlignment(JLabel.CENTER);
	    textoEncriptado.setHorizontalAlignment(JLabel.CENTER);
        
	    textoDescriptografado  = new JLabel();
	    textoDescriptografado.setBorder(border);
	    textoDescriptografado.setVerticalAlignment(JLabel.CENTER);
	    textoDescriptografado.setHorizontalAlignment(JLabel.CENTER);
	    
		button = new JButton("Criptografar");
		button.addActionListener(this);
		
		
		textField = new JTextField();
		textField.setPreferredSize(new Dimension(250,40));
		textField.setFont(new Font("Consolas",Font.PLAIN,35));
		textField.setForeground(new Color(0x00FF00));
		textField.setBackground(Color.black);
		textField.setCaretColor(Color.white);
		textField.setBorder(border);
		textField.setText("");
		
		
		this.add(textField);
		this.add(button);
		this.setVisible(true);
	}
	
	 public static byte[] encrypt(String textopuro, String chaveencriptacao) throws Exception {
	    Cipher encripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
	    SecretKeySpec key = new SecretKeySpec(chaveencriptacao.getBytes("UTF-8"), "AES");
	    encripta.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
	    return encripta.doFinal(textopuro.getBytes("UTF-8"));
	 }	
	 
	public static String decrypt(byte[] textoencriptado, String chaveencriptacao) throws Exception {
	    Cipher decripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
	    SecretKeySpec key = new SecretKeySpec(chaveencriptacao.getBytes("UTF-8"), "AES");
	    decripta.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
	    return new String(decripta.doFinal(textoencriptado), "UTF-8");
	  }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==button) {
			System.out.println("Esse texto será criptogrado "+ textField.getText());
			setTextoPuro(textField.getText());
			try {
				setTextoCriptografado(encrypt(getTextoPuro(),getChaveDeCriptografia()));	
				textoEncriptado.setText( getTextoCriptografado().toString());
				textoDescriptografado.setText(decrypt(getTextoCriptografado(), getChaveDeCriptografia()));
				
				System.out.println("A chave usada para criptografar: " + getChaveDeCriptografia());
				System.out.println("A mensagem original: " + getTextoPuro());
				System.out.println("O texto criptografado: "+ getTextoCriptografado());
				System.out.println("A mensagem descriptografada: "+ textoDescriptografado.getText());
			} catch (Exception erro) {
				erro.printStackTrace();
			}
			
			
			this.add(label);
			this.add(textoEncriptado);
			this.add(label2);
			this.add(textoDescriptografado);
			this.setLayout(new GridLayout(4,4));
			this.revalidate();
		}
		
	}
}