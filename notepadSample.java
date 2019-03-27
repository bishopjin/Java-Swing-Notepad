import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class notepadSample extends JFrame implements ActionListener {
	private JButton open_btn;
	private JButton save_btn;
	private JButton new_btn;
	private JButton quit_btn;
	private JButton fg_btn;
	private JButton bg_btn;
	private JPanel northpanel;
	private JPanel centerpanel;
	private JPanel southpanel;
	private JPanel mix_north;
	private JPanel mix_south;
	private JTextArea textarea;
	private JScrollPane scroll; //ADD SCROLL BAR TO TEXTAREA WHEN OVERFLOW
	private JTextField font_size;
	private JLabel font_size_label;
	private JFileChooser chooser;
	private JFileChooser SaveAs;
	File file;

	public notepadSample(){
		setSize(600, 400);
		setTitle("NatPad");
		//setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		new_btn = new JButton("New");
		open_btn = new JButton("Open");
		save_btn = new JButton("Save");
		quit_btn = new JButton("Quit");
		fg_btn = new JButton("Foreground");
		bg_btn = new JButton("Background");
		textarea = new JTextArea();
		font_size = new JTextField("12", 4);
		font_size_label = new JLabel("Font Size: ");

		northpanel = new JPanel(new GridLayout(1, 4));
		northpanel.add(new_btn);
		northpanel.add(save_btn);
		northpanel.add(open_btn);
		northpanel.add(quit_btn);
		this.add(northpanel, BorderLayout.NORTH);

		centerpanel = new JPanel(new BorderLayout());
		centerpanel.add(textarea);
		scroll = new JScrollPane (textarea);
		this.add(scroll, BorderLayout.CENTER);

		mix_north = new JPanel();
		mix_north.add(fg_btn);
		mix_north.add(bg_btn);

		mix_south = new JPanel();
		mix_south.add(font_size_label);
		mix_south.add(font_size);

		southpanel = new JPanel(new BorderLayout());
		southpanel.add(mix_north, BorderLayout.NORTH);
		southpanel.add(mix_south, BorderLayout.SOUTH);
		this.add(southpanel, BorderLayout.SOUTH);
		setVisible(true);

		quit_btn.addActionListener(this);
		save_btn.addActionListener(this);
		new_btn.addActionListener(this);
		open_btn.addActionListener(this);
		font_size.addActionListener(this);
		fg_btn.addActionListener(this);
		bg_btn.addActionListener(this);

	}
	public void actionPerformed(ActionEvent event){
		String s = event.getActionCommand();

		if (s == "Quit"){
			int dialogButton = JOptionPane.YES_NO_OPTION;
			int dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure?", "Close Application", dialogButton);
			if(dialogResult == 0) {
  				System.exit(0);
			} 
		}
		else if (s == "Save"){
			savetofile();
		}
		else if (s == "New"){
			if (textarea.getText().trim().length() > 0){
				savetofile();
			}
		}
		else if (s == "Open"){
			openfile();
		}
		else if (s == "Foreground"){
			Color c = JColorChooser.showDialog(null, "Choose a Color", textarea.getForeground());
			textarea.setForeground(c);
		}
		else if (s == "Background"){
			Color c = JColorChooser.showDialog(null, "Choose a Color", textarea.getBackground());
			textarea.setBackground(c);	
		}
		else{
			String string_size = font_size.getText();
			float size = Float.parseFloat(string_size);
			textarea.setFont(textarea.getFont().deriveFont(size));
		}
	}

	public void openfile(){
		chooser = new JFileChooser();
		int returnVal = chooser.showOpenDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION){  
			file = chooser.getSelectedFile(); 
			String path = file.getAbsolutePath();   
		}
		/*
		FileInputStream fin=new FileInputStream(path);  
		BufferedReader din=new BufferedReader(new InputStreamReader(fin));  
		String str=" ";  
		while(str!=null){  
			str=din.readLine();  
			if(str==null)  
				break;  
			textarea.append(str+"\n");  
		}  
		*/
		textarea.setText("I'm bugged you know, please fix me Senpai!");
	}

	public void savetofile(){
		SaveAs = new JFileChooser();
      	SaveAs.setApproveButtonText("Save");
      	int actionDialog = SaveAs.showSaveDialog(this);
      	if (actionDialog != JFileChooser.APPROVE_OPTION) {
        	return;
      	}

      	File fileName = new File(SaveAs.getSelectedFile() + ".txt");
      	BufferedWriter outFile = null;
      	try {
         	outFile = new BufferedWriter(new FileWriter(fileName));

         	textarea.write(outFile);   // *** here: ***

      	} catch (Exception ex) {
         	ex.printStackTrace();
      	} finally {
         	if (outFile != null) {
            	try {
               		outFile.close();
               		textarea.setText("");
            	} catch (Exception e) {
               		// one of the few times that I think that it's OK
               		// to leave this blank
            	}
        	}
      	}
	}

	public static void main(String[] args){
		notepadSample gui = new notepadSample();
	}
	
}