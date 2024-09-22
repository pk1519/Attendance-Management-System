import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AttendanceManagementSystem2 extends JFrame {
    private final DefaultComboBoxModel<Student> studentComboBoxModel;
    private final DefaultListModel<Student> presentStudentsModel;
    private final DefaultListModel<Student> absentStudentsModel;

    public AttendanceManagementSystem2() {
        setTitle("Attendance Management System");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Map<Integer, Student> students = new HashMap<>();
        students.put(1, new Student("ROHIT SHARMA", "22BCS11212"));
        students.put(2, new Student("SHUBHMAN GILL", "22BCS1212"));
        students.put(3, new Student("VIRAT KOHLI", "22BCS18656"));
        students.put(4, new Student("KL RAHUL", "22BCS17653"));
        students.put(5, new Student("SHREYAS IYER ", "7"));
        students.put(6, new Student("SURYA KUMAR YADVA", "Roll No 723432"));
        students.put(7, new Student("RAVINDRA JADEJA", "Roll No 73432"));
        students.put(8, new Student("KULDEEP YADAV", "Roll No 723432"));
        students.put(9, new Student("JASPRIT BUMRAH", "Roll No 723432"));
        students.put(10, new Student("M SHAMI", "Roll No 723432"));
        students.put(11, new Student("M SIRAJ", "Roll No 45"));

        studentComboBoxModel = new DefaultComboBoxModel<>(students.values().toArray(new Student[0]));
        presentStudentsModel = new DefaultListModel<>();
        absentStudentsModel = new DefaultListModel<>();

        setupUI();
    }

    private void setupUI() {
        // Modify the interface color to grey
        Color greyColor = new Color(169, 169, 169); // Using a shade of grey

        // Setting background color for panels and buttons
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(greyColor);

        JPanel studentPanel = new JPanel(new FlowLayout());
        studentPanel.setBackground(greyColor);

        JPanel reportPanel = new JPanel(new FlowLayout());
        reportPanel.setBackground(greyColor);

        JPanel addStudentPanel = new JPanel(new FlowLayout());
        addStudentPanel.setBackground(greyColor);

        JButton markPresentButton = new JButton("Mark Present");
        markPresentButton.setBackground(Color.cyan); // Keeping specific colors for buttons

        JButton markAbsentButton = new JButton("Mark Absent");
        markAbsentButton.setBackground(Color.RED); // Keeping specific colors for buttons

        JButton viewReportButton = new JButton("View Attendance Report");
        JButton generateQRCodeButton = new JButton("Generate QR Code");

        // Setting background color for lists
        JList<Student> presentStudentsList = new JList<>(presentStudentsModel);
        presentStudentsList.setBackground(Color.blue); // Setting list background color

        JList<Student> absentStudentsList = new JList<>(absentStudentsModel);
        absentStudentsList.setBackground(Color.green); // Setting list background color

        // Adding components to panels
        studentPanel.add(new JLabel("Select Student:"));
        JComboBox<Student> studentComboBox = new JComboBox<>(studentComboBoxModel);
        studentPanel.add(studentComboBox);
        studentPanel.add(markPresentButton);
        studentPanel.add(markAbsentButton);

        JPanel attendancePanel = new JPanel(new GridLayout(1, 2)); // Add this line
        attendancePanel.setBackground(greyColor); // Set background color for attendancePanel
        attendancePanel.add(new JScrollPane(presentStudentsList));
        attendancePanel.add(new JScrollPane(absentStudentsList));

        reportPanel.add(viewReportButton);
        reportPanel.add(generateQRCodeButton);

        mainPanel.add(studentPanel, BorderLayout.NORTH);
        mainPanel.add(attendancePanel, BorderLayout.CENTER); // Change here from attendancePanel to mainPanel
        mainPanel.add(reportPanel, BorderLayout.SOUTH);

        add(mainPanel);

        markPresentButton.addActionListener(e -> {
            Student selectedStudent = (Student) studentComboBox.getSelectedItem();
            presentStudentsModel.addElement(selectedStudent);
            absentStudentsModel.removeElement(selectedStudent);
        });

        markAbsentButton.addActionListener(e -> {
            Student selectedStudent = (Student) studentComboBox.getSelectedItem();
            absentStudentsModel.addElement(selectedStudent);
            presentStudentsModel.removeElement(selectedStudent);
        });

        viewReportButton.addActionListener(e -> displayReport());

        generateQRCodeButton.addActionListener(e -> {
            generateQRCode(); // Call generateQRCode method
        });
    }

    private void displayReport() {
        JFrame reportFrame = new JFrame("Attendance Report");
        reportFrame.setSize(400, 300);
        reportFrame.setLocationRelativeTo(this);

        JTextArea reportTextArea = new JTextArea();
        reportTextArea.setEditable(false);

        for (int i = 0; i < presentStudentsModel.size(); i++) {
            reportTextArea.append(presentStudentsModel.get(i).name() + " (Present)\n");
        }
        for (int i = 0; i < absentStudentsModel.size(); i++) {
            reportTextArea.append(absentStudentsModel.get(i).name() + " (Absent)\n");
        }

        reportFrame.add(new JScrollPane(reportTextArea));
        reportFrame.setVisible(true);
    }

    private void generateQRCode() {
        // Retrieve selected student from the combo box
        Student selectedStudent = (Student) studentComboBoxModel.getSelectedItem();
        // Prepare the student information for the QR code
        String studentInfo = "Name: " + selectedStudent.name() + "\nRoll No: " + selectedStudent.rollNo();

        // Create a BufferedImage to generate the QR code
        BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.GREEN);
        g2d.fillRect(0, 0, 200, 200);
        g2d.setColor(Color.RED);
        g2d.setFont(new Font("ALGERIAN", Font.PLAIN, 12));
        g2d.drawString(studentInfo, 10, 20);

        // Save the generated QR code as an image file
        try {
            File outputFile = new File("qrcode.png");
            javax.imageio.ImageIO.write(image, "png", outputFile);

            JOptionPane.showMessageDialog(this, "QR Code generated and saved as 'qrcode.png'", "QR Code Generated", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to generate QR Code.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Remaining methods and Student class remain unchanged

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AttendanceManagementSystem2 ams = new AttendanceManagementSystem2();
            ams.setVisible(true);
        });
    }

    private record Student(String name, String rollNo) {

        @Override
            public String toString() {
                return name;
            }
        }
}
