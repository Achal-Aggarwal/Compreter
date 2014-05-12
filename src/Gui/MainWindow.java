package Gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.GridBagLayout;

import javax.swing.JTextArea;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;

import compreter.Compreter;

public class MainWindow {

	private JFrame frmCompreterA;

	/**
	 * Launch the application.
	 */
	public static void init(final String initCode) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow(initCode);
					window.frmCompreterA.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow(String initCode) {
		initialize(initCode);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String initCode) {
		frmCompreterA = new JFrame();
		frmCompreterA.setTitle("Compreter - A Javascript Compiler");
		frmCompreterA.setBounds(100, 100, 634, 390);
		frmCompreterA.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnCompile = new JButton("Compile");
		
		JLabel origSize = new JLabel("New label");
		
		final JLabel newSize = new JLabel("New label");
		
		JScrollPane scrollPane = new JScrollPane();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(frmCompreterA.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(origSize, Alignment.TRAILING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE))
					.addGap(6)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE))
						.addComponent(newSize))
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(263)
					.addComponent(btnCompile, GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
					.addGap(263))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(origSize)
						.addComponent(newSize))
					.addGap(19)
					.addComponent(btnCompile)
					.addContainerGap())
		);
		
		final JTextArea newText = new JTextArea();
		scrollPane_1.setViewportView(newText);
		newText.setEditable(false);
		
		final JTextArea origText = new JTextArea(initCode);
		scrollPane.setViewportView(origText);
		origSize.setText("Size:" + origText.getText().length());
		newSize.setText("Size:0");
		frmCompreterA.getContentPane().setLayout(groupLayout);
		
		btnCompile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				newText.setText(Compreter.run(origText.getText()));
				newSize.setText("Size:" + newText.getText().length());
			}
		});
	}
}
