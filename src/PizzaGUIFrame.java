import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PizzaGUIFrame extends JFrame
{

    private JRadioButton thinCrustRadio, regularCrustRadio, deepDishCrustRadio;
    private JComboBox<String> sizeComboBox;
    private JCheckBox pepperoniTopping, mushroomsTopping, onionsTopping, sausageTopping, olivesTopping, extraCheeseTopping;

    private JEditorPane orderTextArea;

    public PizzaGUIFrame()
    {
        setTitle("Monster Pizza Order Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel crustPanel = createCrustPanel();
        JPanel sizePanel = createSizePanel();
        JPanel toppingsPanel = createToppingsPanel();
        JPanel orderDisplayPanel = createOrderDisplayPanel();
        JPanel buttonPanel = createButtonPanel();

        add(crustPanel, BorderLayout.WEST);
        add(sizePanel, BorderLayout.CENTER);
        add(toppingsPanel, BorderLayout.EAST);
        add(orderDisplayPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createCrustPanel()
    {
        JPanel crustPanel = new JPanel(new GridLayout(3, 1));
        crustPanel.setBorder(BorderFactory.createTitledBorder("Crust"));

        thinCrustRadio = new JRadioButton("Thin");
        regularCrustRadio = new JRadioButton("Regular");
        deepDishCrustRadio = new JRadioButton("Deep-dish");

        ButtonGroup crustGroup = new ButtonGroup();
        crustGroup.add(thinCrustRadio);
        crustGroup.add(regularCrustRadio);
        crustGroup.add(deepDishCrustRadio);

        crustPanel.add(thinCrustRadio);
        crustPanel.add(regularCrustRadio);
        crustPanel.add(deepDishCrustRadio);

        return crustPanel;
    }

    private JPanel createSizePanel()
    {
        JPanel sizePanel = new JPanel(new GridLayout(2, 1));
        sizePanel.setBorder(BorderFactory.createTitledBorder("Size"));

        String[] sizes = {"Small", "Medium", "Large", "Monster"};
        sizeComboBox = new JComboBox<>(sizes);

        sizePanel.add(sizeComboBox);

        return sizePanel;
    }

    private JPanel createToppingsPanel()
    {
        JPanel toppingsPanel = new JPanel(new GridLayout(6, 1));
        toppingsPanel.setBorder(BorderFactory.createTitledBorder("Toppings"));

        pepperoniTopping = new JCheckBox("Pepperoni");
        mushroomsTopping = new JCheckBox("Mushrooms");
        onionsTopping = new JCheckBox("Onions");
        sausageTopping = new JCheckBox("Sausage");
        olivesTopping = new JCheckBox("Olives");
        extraCheeseTopping = new JCheckBox("Extra Cheese");

        toppingsPanel.add(pepperoniTopping);
        toppingsPanel.add(mushroomsTopping);
        toppingsPanel.add(onionsTopping);
        toppingsPanel.add(sausageTopping);
        toppingsPanel.add(olivesTopping);
        toppingsPanel.add(extraCheeseTopping);

        return toppingsPanel;
    }

    private JPanel createOrderDisplayPanel()
    {
        JPanel orderDisplayPanel = new JPanel(new BorderLayout());
        orderDisplayPanel.setBorder(BorderFactory.createTitledBorder("Order Details"));

        orderTextArea = new JEditorPane();
        orderTextArea.setEditable(false);
        orderTextArea.setContentType("text/html");

        JScrollPane scrollPane = new JScrollPane(orderTextArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        orderDisplayPanel.add(scrollPane);


        return orderDisplayPanel;
    }

    private JPanel createButtonPanel()
    {
        JPanel buttonPanel = new JPanel();
        JButton orderButton = new JButton("Order");
        JButton clearButton = new JButton("Clear");
        JButton quitButton = new JButton("Quit");

        orderButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                displayOrder();
            }
        });

        clearButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                clearForm();
            }
        });

        quitButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION)
                {
                    System.exit(0);
                }
            }
        });

        buttonPanel.add(orderButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(quitButton);

        return buttonPanel;
    }

    private void displayOrder()
    {
        if (!thinCrustRadio.isSelected() && !regularCrustRadio.isSelected() && !deepDishCrustRadio.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please select a crust type.", "Incomplete Order", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (sizeComboBox.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Please select a pizza size.", "Incomplete Order", JOptionPane.WARNING_MESSAGE);
            return;
        }

        StringBuilder orderDetails = new StringBuilder();
        double subTotal = calculateSubtotal();
        double tax = subTotal * 0.07;
        double total = subTotal + tax;

        orderDetails.append("<html><body>");
        orderDetails.append("<b>Monster Pizza Order Receipt</b><br><br>");
        orderDetails.append("<b>Crust:</b> ");
        if (thinCrustRadio.isSelected()) orderDetails.append("Thin<br>");
        else if (regularCrustRadio.isSelected()) orderDetails.append("Regular<br>");
        else orderDetails.append("Deep-dish<br>");

        orderDetails.append("<b>Size:</b> ").append(sizeComboBox.getSelectedItem()).append("<br>");

        orderDetails.append("<b>Toppings:</b><br>");
        orderDetails.append(getToppingString(pepperoniTopping, "Pepperoni"));
        orderDetails.append(getToppingString(mushroomsTopping, "Mushrooms"));
        orderDetails.append(getToppingString(onionsTopping, "Onions"));
        orderDetails.append(getToppingString(sausageTopping, "Sausage"));
        orderDetails.append(getToppingString(olivesTopping, "Olives"));
        orderDetails.append(getToppingString(extraCheeseTopping, "Extra Cheese"));

        orderDetails.append("<br><b>Subtotal:</b> $").append(String.format("%.2f", subTotal)).append("<br>");
        orderDetails.append("<b>Tax (7%):</b> $").append(String.format("%.2f", tax)).append("<br>");
        orderDetails.append("<b>Total:</b> $").append(String.format("%.2f", total));
        orderDetails.append("</body></html>");

        orderTextArea.setText(orderDetails.toString());
    }

    private double calculateSubtotal()
    {
        double baseCost;
        switch (sizeComboBox.getSelectedIndex())
        {
            case 0:
                baseCost = 8.00;
                break;
            case 1:
                baseCost = 12.00;
                break;
            case 2:
                baseCost = 16.00;
                break;
            case 3:
                baseCost = 20.00;
                break;
            default:
                baseCost = 0.00;
        }

        double toppingCost = (pepperoniTopping.isSelected() ? 1.00 : 0.00) +
                (mushroomsTopping.isSelected() ? 1.00 : 0.00) +
                (onionsTopping.isSelected() ? 1.00 : 0.00) +
                (sausageTopping.isSelected() ? 1.00 : 0.00) +
                (olivesTopping.isSelected() ? 1.00 : 0.00) +
                (extraCheeseTopping.isSelected() ? 1.00 : 0.00);

        return baseCost + toppingCost;
    }

    private String getToppingString(JCheckBox checkBox, String toppingName)
    {
        return checkBox.isSelected() ? toppingName + "<br>" : "";
    }

    private void clearForm()
    {
        thinCrustRadio.setSelected(false);
        regularCrustRadio.setSelected(false);
        deepDishCrustRadio.setSelected(false);
        sizeComboBox.setSelectedIndex(-1);
        pepperoniTopping.setSelected(false);
        mushroomsTopping.setSelected(false);
        onionsTopping.setSelected(false);
        sausageTopping.setSelected(false);
        olivesTopping.setSelected(false);
        extraCheeseTopping.setSelected(false);
        orderTextArea.setText("");
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run() {
                new PizzaGUIFrame();
            }
        });
    }
}
