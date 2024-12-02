

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GraphVisualizer extends JFrame {
    private final Map<String, Set<String>> graph; // Graph structure
    private final JTextField node1Field, node2Field;
    private final JButton addEdgeButton, removeEdgeButton, bfsButton, dfsButton;
    private final DrawPanel drawPanel;

    public GraphVisualizer() {
        graph = new HashMap<>();

        // Frame settings
        setTitle("Graph Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 3));

        node1Field = new JTextField();
        node2Field = new JTextField();

        addEdgeButton = new JButton("Add Edge");
        removeEdgeButton = new JButton("Remove Edge");
        bfsButton = new JButton("Run BFS");
        dfsButton = new JButton("Run DFS");

        inputPanel.add(new JLabel("Node 1:"));
        inputPanel.add(node1Field);
        inputPanel.add(addEdgeButton);

        inputPanel.add(new JLabel("Node 2:"));
        inputPanel.add(node2Field);
        inputPanel.add(removeEdgeButton);

        // Bottom panel for BFS/DFS
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 2));
        bottomPanel.add(bfsButton);
        bottomPanel.add(dfsButton);

        // Drawing panel
        drawPanel = new DrawPanel();
        JScrollPane scrollPane = new JScrollPane(drawPanel);

        // Add components to frame
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Button Actions
        addEdgeButton.addActionListener(e -> addEdge());
        removeEdgeButton.addActionListener(e -> removeEdge());
        bfsButton.addActionListener(e -> runBFS());
        dfsButton.addActionListener(e -> runDFS());
    }

    // Add an edge to the graph
    private void addEdge() {
        String node1 = node1Field.getText().trim();
        String node2 = node2Field.getText().trim();

        if (node1.isEmpty() || node2.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Both nodes are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        graph.putIfAbsent(node1, new HashSet<>());
        graph.putIfAbsent(node2, new HashSet<>());

        graph.get(node1).add(node2);
        graph.get(node2).add(node1);

        node1Field.setText("");
        node2Field.setText("");

        drawPanel.repaint();
    }

    // Remove an edge from the graph
    private void removeEdge() {
        String node1 = node1Field.getText().trim();
        String node2 = node2Field.getText().trim();

        if (node1.isEmpty() || node2.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Both nodes are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (graph.containsKey(node1))
            graph.get(node1).remove(node2);
        if (graph.containsKey(node2))
            graph.get(node2).remove(node1);

        node1Field.setText("");
        node2Field.setText("");

        drawPanel.repaint();
    }

    // Run BFS from a starting node
    private void runBFS() {
        String startNode = node1Field.getText().trim();
        if (startNode.isEmpty() || !graph.containsKey(startNode)) {
            JOptionPane.showMessageDialog(this, "Start node is invalid or does not exist!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        StringBuilder result = new StringBuilder("BFS Traversal: ");

        queue.add(startNode);
        visited.add(startNode);

        while (!queue.isEmpty()) {
            String node = queue.poll();
            result.append(node).append(" ");
            for (String neighbor : graph.get(node)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        JOptionPane.showMessageDialog(this, result.toString(), "BFS Result", JOptionPane.INFORMATION_MESSAGE);
    }

    // Run DFS from a starting node
    private void runDFS() {
        String startNode = node1Field.getText().trim();
        if (startNode.isEmpty() || !graph.containsKey(startNode)) {
            JOptionPane.showMessageDialog(this, "Start node is invalid or does not exist!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Set<String> visited = new HashSet<>();
        StringBuilder result = new StringBuilder("DFS Traversal: ");

        dfsHelper(startNode, visited, result);

        JOptionPane.showMessageDialog(this, result.toString(), "DFS Result", JOptionPane.INFORMATION_MESSAGE);
    }

    // DFS helper method
    private void dfsHelper(String node, Set<String> visited, StringBuilder result) {
        visited.add(node);
        result.append(node).append(" ");
        for (String neighbor : graph.get(node)) {
            if (!visited.contains(neighbor)) {
                dfsHelper(neighbor, visited, result);
            }
        }
    }

    // Drawing panel for visualization
    private class DrawPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int width = getWidth();
            int height = getHeight();

            // Node positions
            Map<String, Point> positions = new HashMap<>();
            int radius = 200;
            int centerX = width / 2;
            int centerY = height / 2;

            int i = 0;
            for (String node : graph.keySet()) {
                double angle = 2 * Math.PI * i / graph.size();
                int x = centerX + (int) (radius * Math.cos(angle));
                int y = centerY + (int) (radius * Math.sin(angle));
                positions.put(node, new Point(x, y));
                i++;
            }

            // Draw edges
            g.setColor(Color.BLACK);
            for (String node : graph.keySet()) {
                for (String neighbor : graph.get(node)) {
                    Point p1 = positions.get(node);
                    Point p2 = positions.get(neighbor);
                    g.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
            }

            // Draw nodes
            g.setColor(Color.BLUE);
            for (String node : graph.keySet()) {
                Point p = positions.get(node);
                g.fillOval(p.x - 15, p.y - 15, 30, 30);
                g.setColor(Color.WHITE);
                g.drawString(node, p.x - 5, p.y + 5);
                g.setColor(Color.BLUE);
            }
        }
    }

    // Main method to run the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GraphVisualizer frame = new GraphVisualizer();
            frame.setVisible(true);
        });
    }
}
