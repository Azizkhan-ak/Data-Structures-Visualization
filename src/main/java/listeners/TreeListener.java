package listeners;
import datastructures.BinaryTree;
import datastructures.RedBlackTree;
import dtos.Node;
import dtos.UserDto;
import panel.VisualizationPanelBinaryTreeImpl;
import panel.VisualizationPanelRedBlackTreeImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class TreeListener extends   MouseAdapter {

    BinaryTree binaryTreeInstance = new BinaryTree();
    RedBlackTree redBlackTreeInstance = new RedBlackTree();
    JFrame mainFrame;
    JPopupMenu popupMenu;
    JButton treeButton;
    URL imageUrl;

    String backGrounImage = "mainFrameBackgound.png";

    Graphics graphicsForVisualizationPanel;

    public TreeListener(JFrame mainFrame, JButton treeButton) {
        ClassLoader classLoader = TreeListener.class.getClassLoader();
        this.imageUrl = classLoader.getResource(backGrounImage);
        this.mainFrame = mainFrame;
        this.treeButton = treeButton;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Show the popup menu at the specified location relative to the parent component
        popupMenu = createPopupMenu();
        popupMenu.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                // When the mouse moves over the popup menu, it's still considered to be over the menu
                if (!treeButton.contains(e.getPoint())) {
                    // If the mouse is not over the tree button, hide the popup menu
                    popupMenu.setVisible(false);
                }
            }
        });
        popupMenu.show(treeButton, 140, 0);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (!isMouseOverPopupMenu()) {
            popupMenu.setVisible(false); // Hide the popup menu when mouse exits
        }
    }

    private boolean isMouseOverPopupMenu() {
        Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(mouseLocation, treeButton);
        return treeButton.contains(mouseLocation);
    }

    private JPopupMenu createPopupMenu() {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem avl = new JMenuItem("AVL Tree");
        avl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog treeFrame = new JDialog(mainFrame, "AVL Structure Visualization", Dialog.ModalityType.APPLICATION_MODAL);
                treeFrame.setPreferredSize(new Dimension(800, 600));
                treeFrame.setLayout(new BorderLayout());
                JPanel contentPane = new JPanel();
                contentPane.setBackground(Color.CYAN);
                treeFrame.setContentPane(contentPane);
                treeFrame.pack();
                treeFrame.setResizable(false);
                treeFrame.setEnabled(true);
                treeFrame.setTitle("AVL Structure Visualization");
                treeFrame.setLocationRelativeTo(mainFrame);
                treeFrame.setVisible(true);
                treeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });

        JMenuItem binaryTree = new JMenuItem("Binary Tree");
        binaryTree.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog treeFrame = new JDialog(mainFrame, "Binary Tree Structure Visualization", Dialog.ModalityType.APPLICATION_MODAL);
                // Create visualization panel
                VisualizationPanelBinaryTreeImpl visualizationPanel = new VisualizationPanelBinaryTreeImpl(binaryTreeInstance);
                visualizationPanel.setBackground(Color.CYAN);
                visualizationPanel.setVisible(true);
                visualizationPanel.repaint();

                // Create operations panel
                JPanel operationsPanel = new JPanel() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        // Draw the background image
                        g.drawImage(new ImageIcon(imageUrl).getImage(), 0, 0, getWidth(), getHeight(), this);
                        // Add a semi-transparent overlay
                        g.setColor(new Color(0, 0, 0, 200)); // Black color with 100 (out of 255) transparency
                        g.fillRect(0, 0, getWidth(), getHeight());
                    }
                };
                operationsPanel.setLayout(null);
                JButton insert = new JButton("Insert");
                insert.setForeground(Color.WHITE);
//                insert.setLocation(100,100);
                insert.setEnabled(true);
                insert.setVisible(true);
                insert.setBorderPainted(true);
                insert.setContentAreaFilled(false);
                insert.setOpaque(false);

                JButton delete = new JButton("Delete");
                delete.setForeground(Color.WHITE);
                delete.setEnabled(true);
                delete.setVisible(true);
                delete.setBorderPainted(true);
                delete.setContentAreaFilled(false);
                delete.setOpaque(false);

                JButton traverse = new JButton("Traverse");
                traverse.setForeground(Color.WHITE);
                traverse.setEnabled(true);
                traverse.setVisible(true);
                traverse.setBorderPainted(true);
                traverse.setContentAreaFilled(false);
                traverse.setOpaque(false);

                JButton update = new JButton("Update");
                update.setForeground(Color.WHITE);
                update.setEnabled(true);
                update.setVisible(true);
                update.setBorderPainted(true);
                update.setContentAreaFilled(false);
                update.setOpaque(false);

                insert.setBounds(10, 10, 100, 30); // x, y, width, height
                delete.setBounds(10, 50, 100, 30);
                traverse.setBounds(10, 90, 100, 30);
                update.setBounds(10, 130, 100, 30);
                operationsPanel.add(insert);
                operationsPanel.add(delete);
                operationsPanel.add(traverse);
                operationsPanel.add(update);
                operationsPanel.setBackground(Color.RED);
                operationsPanel.setVisible(true);

                JPanel insertPanel = new JPanel(new GridLayout(6, 2)); // Use GridLayout for arranging fields in rows and columns
                JPanel deletePanel = new JPanel(new GridLayout(4,2 ));
                insert.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Add labels and text fields for name, dob, and email
                        insertPanel.removeAll();
                        deletePanel.removeAll();
                        operationsPanel.remove(insertPanel);
                        operationsPanel.remove(deletePanel);
                        operationsPanel.revalidate();
                        operationsPanel.repaint();
                        JLabel cnicLabel = new JLabel("CNIC");
                        JTextField cnicTextField = new JTextField();
                        JLabel nameLabel = new JLabel("Name");
                        JTextField nameTextField = new JTextField();
                        JLabel dobLabel = new JLabel("DOB");
                        JTextField dobTextField = new JTextField();
                        JLabel emailLabel = new JLabel("Email");
                        JTextField emailTextField = new JTextField();
                        JButton push = new JButton("Push");

                        // Add labels and text fields to the insert panel
                        insertPanel.add(cnicLabel);
                        insertPanel.add(cnicTextField);
                        insertPanel.add(nameLabel);
                        insertPanel.add(nameTextField);
                        insertPanel.add(dobLabel);
                        insertPanel.add(dobTextField);
                        insertPanel.add(emailLabel);
                        insertPanel.add(emailTextField);
                        insertPanel.add(push);
                        insertPanel.setBounds(10, 170, 220, 300);
                        // Add the insert panel to the operations panel
                        insertPanel.revalidate();
                        insertPanel.setVisible(true);
                        operationsPanel.add(insertPanel);
                        // Repaint the operations panel to reflect the changes
                        operationsPanel.revalidate();
                        operationsPanel.repaint();

                        //add push button action Listener
                        push.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                //check if text fields are not null
                                if ((nameTextField.getText() != null && !nameTextField.getText().isEmpty()) &&
                                        (emailTextField.getText() != null && !emailTextField.getText().isEmpty()) &&
                                        (cnicTextField.getText() != null && !cnicTextField.getText().isEmpty())) {
                                    UserDto userDto = new UserDto();
                                    userDto.setId(Long.valueOf(cnicTextField.getText().toString()));
                                    userDto.setName(nameTextField.getText().toString());
                                    userDto.setEmail(emailTextField.getText().toString());
                                    userDto.setDob(dobTextField.getText());
                                    Boolean result = binaryTreeInstance.insertIntoBinaryTree(userDto);
                                    if (!result) {
                                        //means that email or name is empty so show dialog popup
                                        JOptionPane.showMessageDialog(treeFrame, "========= Identifier already exist ============ ", "Warning", JOptionPane.WARNING_MESSAGE);
                                    } else {
                                        //create visualtion
                                        visualizationPanel.repaint();
                                        cnicTextField.setText(null);
                                        nameTextField.setText(null);
                                        dobTextField.setText(null);
                                        emailTextField.setText(null);
                                    }
                                } else {
                                    //means that email or name is empty so show dialog popup
                                    JOptionPane.showMessageDialog(treeFrame, "Please provide name / email / CNIC ", "Warning", JOptionPane.WARNING_MESSAGE);
                                }
                            }
                        });
                    }
                });

                //Set up Delete Action Listener
                delete.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        insertPanel.removeAll();
                        deletePanel.removeAll();
                        operationsPanel.remove(insertPanel);
                        operationsPanel.remove(deletePanel);
                        operationsPanel.revalidate();
                        operationsPanel.repaint();
                        JLabel cnicLabel = new JLabel("CNIC");
                        JTextField cnicTextField = new JTextField();
                        JButton delete = new JButton("Delete");
                        deletePanel.add(cnicLabel);
                        deletePanel.add(cnicTextField);
                        deletePanel.add(delete);
                        deletePanel.setBounds(10, 170, 220, 300);
                        deletePanel.revalidate();
                        deletePanel.setVisible(true);
                        operationsPanel.add(deletePanel);
                        insertPanel.disable();
                        operationsPanel.revalidate();
                        operationsPanel.repaint();
                        delete.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(cnicTextField.getText() != null && !cnicTextField.getText().isEmpty()) {
                                    Long id = Long.valueOf(cnicTextField.getText().toString());
                                    UserDto userDto = new UserDto();
                                    userDto.setId(id);
                                    Boolean result = binaryTreeInstance.deleteFromBinaryTree(userDto);
                                    if(result){
                                        System.out.println("deleted");
                                        visualizationPanel.repaint();
                                        cnicTextField.setText(null);
                                    }
                                    else {
                                        JOptionPane.showMessageDialog(treeFrame, "Key Not Found", "Warning", JOptionPane.WARNING_MESSAGE);
                                    }
                                }
                                else {
                                    JOptionPane.showMessageDialog(treeFrame, "Please provide CNIC ", "Warning", JOptionPane.WARNING_MESSAGE);
                                }
                            }
                        });
                    }
                });

                traverse.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        visualizationPanel.colorNodeWithDelayPreOrder(binaryTreeInstance.root);
                    }
                });
                // Add panels to the dialog
                treeFrame.add(operationsPanel, BorderLayout.EAST);
                treeFrame.add(visualizationPanel, BorderLayout.CENTER);
                // Set preferred sizes for panels
                visualizationPanel.setPreferredSize(new Dimension(treeFrame.getWidth() + 700, treeFrame.getHeight()));

                // Set dialog properties
                treeFrame.setLayout(new BoxLayout(treeFrame.getContentPane(), BoxLayout.X_AXIS));
                treeFrame.setPreferredSize(new Dimension(1200, 700));
                treeFrame.setResizable(false);
                treeFrame.setEnabled(true);
                treeFrame.pack();
                treeFrame.setLocationRelativeTo(mainFrame);
                treeFrame.setTitle("Binary Tree Structure Visualization");
                treeFrame.setVisible(true);
                treeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });


        JMenuItem redBlackTree = new JMenuItem("Red Black Tree");
        redBlackTree.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog treeFrame = new JDialog(mainFrame, "RedBlack Tree Structure Visualization", Dialog.ModalityType.APPLICATION_MODAL);
                // Create visualization panel
                VisualizationPanelRedBlackTreeImpl visualizationPanel = new VisualizationPanelRedBlackTreeImpl(redBlackTreeInstance);
                visualizationPanel.setBackground(Color.CYAN);
                visualizationPanel.setVisible(true);
                visualizationPanel.repaint();

                // Create operations panel
                JPanel operationsPanel = new JPanel() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        // Draw the background image
                        g.drawImage(new ImageIcon(imageUrl).getImage(), 0, 0, getWidth(), getHeight(), this);
                        // Add a semi-transparent overlay
                        g.setColor(new Color(0, 0, 0, 200)); // Black color with 100 (out of 255) transparency
                        g.fillRect(0, 0, getWidth(), getHeight());
                    }
                };
                operationsPanel.setLayout(null);
                JButton insert = new JButton("Insert");
                insert.setForeground(Color.WHITE);
//                insert.setLocation(100,100);
                insert.setEnabled(true);
                insert.setVisible(true);
                insert.setBorderPainted(true);
                insert.setContentAreaFilled(false);
                insert.setOpaque(false);

                JButton delete = new JButton("Delete");
                delete.setForeground(Color.WHITE);
                delete.setEnabled(true);
                delete.setVisible(true);
                delete.setBorderPainted(true);
                delete.setContentAreaFilled(false);
                delete.setOpaque(false);

                JButton traverse = new JButton("Traverse");
                traverse.setForeground(Color.WHITE);
                traverse.setEnabled(true);
                traverse.setVisible(true);
                traverse.setBorderPainted(true);
                traverse.setContentAreaFilled(false);
                traverse.setOpaque(false);

                JButton update = new JButton("Update");
                update.setForeground(Color.WHITE);
                update.setEnabled(true);
                update.setVisible(true);
                update.setBorderPainted(true);
                update.setContentAreaFilled(false);
                update.setOpaque(false);

                insert.setBounds(10, 10, 100, 30); // x, y, width, height
                delete.setBounds(10, 50, 100, 30);
                traverse.setBounds(10, 90, 100, 30);
                update.setBounds(10, 130, 100, 30);
                operationsPanel.add(insert);
                operationsPanel.add(delete);
                operationsPanel.add(traverse);
                operationsPanel.add(update);
                operationsPanel.setBackground(Color.RED);
                operationsPanel.setVisible(true);

                JPanel insertPanel = new JPanel(new GridLayout(6, 2)); // Use GridLayout for arranging fields in rows and columns
                JPanel deletePanel = new JPanel(new GridLayout(4,2 ));
                insert.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Add labels and text fields for name, dob, and email
                        insertPanel.removeAll();
                        deletePanel.removeAll();
                        operationsPanel.remove(insertPanel);
                        operationsPanel.remove(deletePanel);
                        operationsPanel.revalidate();
                        operationsPanel.repaint();
                        JLabel cnicLabel = new JLabel("CNIC");
                        JTextField cnicTextField = new JTextField();
                        JLabel nameLabel = new JLabel("Name");
                        JTextField nameTextField = new JTextField();
                        JLabel dobLabel = new JLabel("DOB");
                        JTextField dobTextField = new JTextField();
                        JLabel emailLabel = new JLabel("Email");
                        JTextField emailTextField = new JTextField();
                        JButton push = new JButton("Push");

                        // Add labels and text fields to the insert panel
                        insertPanel.add(cnicLabel);
                        insertPanel.add(cnicTextField);
                        insertPanel.add(nameLabel);
                        insertPanel.add(nameTextField);
                        insertPanel.add(dobLabel);
                        insertPanel.add(dobTextField);
                        insertPanel.add(emailLabel);
                        insertPanel.add(emailTextField);
                        insertPanel.add(push);
                        insertPanel.setBounds(10, 170, 220, 300);
                        // Add the insert panel to the operations panel
                        insertPanel.revalidate();
                        insertPanel.setVisible(true);
                        operationsPanel.add(insertPanel);
                        // Repaint the operations panel to reflect the changes
                        operationsPanel.revalidate();
                        operationsPanel.repaint();

                        //add push button action Listener
                        push.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                //check if text fields are not null
                                if ((nameTextField.getText() != null && !nameTextField.getText().isEmpty()) &&
                                        (emailTextField.getText() != null && !emailTextField.getText().isEmpty()) &&
                                        (cnicTextField.getText() != null && !cnicTextField.getText().isEmpty())) {
                                    UserDto userDto = new UserDto();
                                    userDto.setId(Long.valueOf(cnicTextField.getText().toString()));
                                    userDto.setName(nameTextField.getText().toString());
                                    userDto.setEmail(emailTextField.getText().toString());
                                    userDto.setDob(dobTextField.getText());
                                    Boolean result = redBlackTreeInstance.insertIntoRedBlackTree(userDto);
                                    if (!result) {
                                        //means that email or name is empty so show dialog popup
                                        JOptionPane.showMessageDialog(treeFrame, "========= Identifier already exist ============ ", "Warning", JOptionPane.WARNING_MESSAGE);
                                    } else {
                                        //create visualtion
                                        visualizationPanel.repaint();
                                        cnicTextField.setText(null);
                                        nameTextField.setText(null);
                                        dobTextField.setText(null);
                                        emailTextField.setText(null);
                                    }
                                } else {
                                    //means that email or name is empty so show dialog popup
                                    JOptionPane.showMessageDialog(treeFrame, "Please provide name / email / CNIC ", "Warning", JOptionPane.WARNING_MESSAGE);
                                }
                            }
                        });
                    }
                });

                //Set up Delete Action Listener
                delete.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        insertPanel.removeAll();
                        deletePanel.removeAll();
                        operationsPanel.remove(insertPanel);
                        operationsPanel.remove(deletePanel);
                        operationsPanel.revalidate();
                        operationsPanel.repaint();
                        JLabel cnicLabel = new JLabel("CNIC");
                        JTextField cnicTextField = new JTextField();
                        JButton delete = new JButton("Delete");
                        deletePanel.add(cnicLabel);
                        deletePanel.add(cnicTextField);
                        deletePanel.add(delete);
                        deletePanel.setBounds(10, 170, 220, 300);
                        deletePanel.revalidate();
                        deletePanel.setVisible(true);
                        operationsPanel.add(deletePanel);
                        insertPanel.disable();
                        operationsPanel.revalidate();
                        operationsPanel.repaint();
                        delete.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(cnicTextField.getText() != null && !cnicTextField.getText().isEmpty()) {
                                    Long id = Long.valueOf(cnicTextField.getText().toString());
                                    UserDto userDto = new UserDto();
                                    userDto.setId(id);
                                    Boolean result = redBlackTreeInstance.deleteFromBinaryTree(userDto);
                                    if(result){
                                        System.out.println("deleted");
                                        visualizationPanel.repaint();
                                        cnicTextField.setText(null);
                                    }
                                    else {
                                        JOptionPane.showMessageDialog(treeFrame, "Key Not Found", "Warning", JOptionPane.WARNING_MESSAGE);
                                    }
                                }
                                else {
                                    JOptionPane.showMessageDialog(treeFrame, "Please provide CNIC ", "Warning", JOptionPane.WARNING_MESSAGE);
                                }
                            }
                        });
                    }
                });

                traverse.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        visualizationPanel.colorNodeWithDelayPreOrder(binaryTreeInstance.root);
                    }
                });
                // Add panels to the dialog
                treeFrame.add(operationsPanel, BorderLayout.EAST);
                treeFrame.add(visualizationPanel, BorderLayout.CENTER);
                // Set preferred sizes for panels
                visualizationPanel.setPreferredSize(new Dimension(treeFrame.getWidth() + 700, treeFrame.getHeight()));

                // Set dialog properties
                treeFrame.setLayout(new BoxLayout(treeFrame.getContentPane(), BoxLayout.X_AXIS));
                treeFrame.setPreferredSize(new Dimension(1200, 700));
                treeFrame.setResizable(false);
                treeFrame.setEnabled(true);
                treeFrame.pack();
                treeFrame.setLocationRelativeTo(mainFrame);
                treeFrame.setTitle("Binary Tree Structure Visualization");
                treeFrame.setVisible(true);
                treeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });
        popupMenu.add(avl);
        popupMenu.add(redBlackTree);
        popupMenu.add(binaryTree);
        return popupMenu;
    }


}
