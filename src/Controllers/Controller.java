package Controllers;

import graphs.GraphWeighted;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Stack;

public class Controller implements Initializable {

    @FXML
    GridPane gridPane;
    private final int GRIDSIZE = 10;

    // Variables related to the source node
    private Boolean sourceSelected = false;
    private Rectangle sourceRectangle;
    private Integer sourceNode;

    // Variables related to the Destination node
    private Boolean destinationSelected = false;
    private Rectangle destinationRectangle;
    private Integer destinationNode;

    // Variables related to the wall node
    private Boolean wallSelected = false;
    private Stack<Integer> walls = new Stack<>();

    // Store the position and Rectangle object
    private final HashMap<Rectangle, Integer> gridNode = new HashMap<>();
    private final HashMap<Integer, Rectangle> rectangleItem = new HashMap<>();


    // Set up the Graph with size of GridSize
    private GraphWeighted graph = GraphWeighted.makeGraph(GRIDSIZE);
    private final Stack<Rectangle> shortestPath = new Stack<>();


    public void addGrid(GridPane gridPane) {
        int count = 0;
        for (int i = 0; i < GRIDSIZE; i++) {
            for (int j = 0; j < GRIDSIZE; j++) {
                Rectangle r1 = new Rectangle(40, 40, Color.WHITE);
                r1.setStroke(Color.BLACK);

                // Assign the count to rectangle in the gridNode Hashmap
                gridNode.put(r1, count);
                rectangleItem.put(count, r1);

                // Change colour of rectangles depending on source,destination or wall
                int tempCount = count;
                r1.setOnMouseClicked(event -> {
                    if (wallSelected & r1.getFill() == Color.BLUE) {
                        r1.setFill(Color.AQUAMARINE);
                    }
                    if (wallSelected & r1.getFill() != Color.RED && r1.getFill() != Color.PURPLE
                            && r1.getFill() != Color.BLACK) {
                        r1.setFill(Color.BLACK);
                        walls.add(tempCount);


                    } else if (sourceSelected && r1.getFill() != Color.BLACK && r1.getFill() != Color.PURPLE) {
                        if (sourceRectangle != null) {
                            sourceRectangle.setFill(Color.WHITE);
                        }
                        r1.setFill(Color.RED);
                        sourceRectangle = r1;
                        sourceNode = gridNode.get(r1);


                    } else if (destinationSelected && r1.getFill() != Color.BLACK && r1.getFill() != Color.RED) {
                        if (destinationRectangle != null) {
                            destinationRectangle.setFill(Color.WHITE);
                        }
                        r1.setFill(Color.PURPLE);
                        destinationRectangle = r1;
                        destinationNode = gridNode.get(r1);
                    }
                });

                // Increment the count
                count++;

                // Add the rectangles to the gridPane
                gridPane.add(r1, j, i);
            }

        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialise the Grid
        addGrid(gridPane);
    }

    public void setSource(MouseEvent mouseEvent) {
        // Set the selected Node as the source
        sourceSelected = true;
        destinationSelected = false;
        wallSelected = false;
    }

    public void setDestination(MouseEvent mouseEvent) {
        // Set the selected Node as the destination
        destinationSelected = true;
        sourceSelected = false;
        wallSelected = false;
    }

    public void getPath(MouseEvent mouseEvent) {
        // Remove walls from graph
        while (walls.size() > 0) {
            Integer wall = walls.pop();
            graph.removeEdge(wall);
        }

        // Delete existing path if present
        if (shortestPath.size() > 0) {
            while (shortestPath.size() > 0) {
                Rectangle rectangle = shortestPath.pop();
                if (rectangle.getFill() != Color.BLACK) {
                    rectangle.setFill(Color.WHITE);
                }
            }
        }

        try {
            // Retrieve the path for the source and destination
            Stack<Integer> path = GraphWeighted.dijkstra(graph, sourceNode, destinationNode);

            if (path != null) {
                while (path.size() > 1) {
                    Rectangle node = rectangleItem.get(path.pop());
                    if (node.getFill() == Color.WHITE) {
                        node.setFill(Color.BLUE);
                        shortestPath.add(node);
                    }
                }

            } else {
                // Display alert message if route is not possible
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Impossible Path");
                alert.setHeaderText(null);
                alert.setContentText("These are not the routes you are looking for!");
                alert.showAndWait();
            }
        } catch (NullPointerException e) {
            // Alert the user that source and destination is required
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Source and Destination Missing");
            alert.setHeaderText(null);
            alert.setContentText("Both a Source and Destination must be selected!");
            alert.showAndWait();
        }
    }

    public void clearGrid(MouseEvent mouseEvent) {
        // Clear and reset the grid and graph
        gridPane.getChildren().clear();
        addGrid(gridPane);
        sourceSelected = false;
        destinationSelected = false;
        wallSelected = false;
        walls = new Stack<>();
        sourceNode = null;
        destinationNode = null;
        graph = GraphWeighted.makeGraph(GRIDSIZE);
    }

    public void setWall(MouseEvent mouseEvent) {
        // Set the selected Node as a wall
        sourceSelected = false;
        destinationSelected = false;
        wallSelected = true;
    }


}
