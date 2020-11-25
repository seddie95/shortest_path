import graphs.GraphWeighted;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Stack;

public class ShortestPath extends Application {
    private final int WIDTH = 700;
    private final int HEIGHT = 700;
    private final int GRIDSIZE = 10;

    private Boolean sourceSelected = false;
    private Rectangle sourceRectangle;
    private Integer sourceNode;


    private Boolean destinationSelected = false;
    private Rectangle destinationRectangle;
    private Integer destinationNode;

    private HashMap<Rectangle, Integer> gridNode = new HashMap<>();
    private HashMap<Integer, Rectangle> rectangleItem = new HashMap<>();


    private Boolean wallSelected = false;
    private Stack<Integer> walls = new Stack<>();


    private GraphWeighted graph = GraphWeighted.makeGraph(GRIDSIZE);
    private Stack<Rectangle> shortestPath = new Stack<>();


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
                    if (wallSelected & r1.getFill() != Color.RED && r1.getFill() != Color.PURPLE) {
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
    public void start(Stage stage) throws Exception {
        stage.setTitle("Shortest Path");
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);

        VBox root = new VBox();
        GridPane gridPane = new GridPane();
        addGrid(gridPane);


        Button sourceButton = new Button("Source");
        Button destinationButton = new Button("Destination");
        Button wallButton = new Button("Add Wall");
        Button pathButton = new Button("Calculate Shortest Path");
        Button clearButton = new Button("Clear Map");


        // buttons to select the rectangle for source
        sourceButton.setOnAction(event -> {
            sourceSelected = true;
            destinationSelected = false;
            wallSelected = false;
        });

        // buttons to select the rectangle for destination
        destinationButton.setOnAction(event -> {
            destinationSelected = true;
            sourceSelected = false;
            wallSelected = false;
        });

        // buttons to select the rectangle for source
        wallButton.setOnAction(event -> {
            sourceSelected = false;
            destinationSelected = false;
            wallSelected = true;
        });

        pathButton.setOnAction(event -> {

            if (walls.size() > 0) {
                for (Integer wall : walls) {
//                    graph.setInfinity(wall);
                    graph.removeEdge(wall);
                }
            }

            // Delete existing path if present
            if (shortestPath.size() > 0) {
                while (shortestPath.size() > 0) {
                    Rectangle rectangle = shortestPath.pop();
                    rectangle.setFill(Color.WHITE);
                }
            }

            try {
                // Retrieve the path for the source and destination
                Stack<Integer> path = GraphWeighted.dijkstra(graph,sourceNode, destinationNode);

                boolean possible = true;
                Stack<Rectangle> possiblePath = new Stack<>();

                // Loop through stack to see if the path does not pass through a wall
                while (possible && path.size() > 1) {
                    Rectangle node = rectangleItem.get(path.pop());
                    if (node.getFill() == Color.BLACK) {
                        possible = false;
                    } else {
                        possiblePath.add(node);
                    }
                }

                // Display the possible route on the grid
                if (possible) {
                    for (Rectangle node : possiblePath) {
                        node.setFill(Color.BLUE);
                        shortestPath.add(node);
                    }

                } else {
                    // Display alert message if route is not possible
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Impossible Path");
                    alert.setHeaderText(null);
                    alert.setContentText("These are not routes you are looking for!");
                    alert.showAndWait();
                }
            } catch (NullPointerException e) {
                System.out.println("Both a Source and Destination must be selected");
            }

        });

        clearButton.setOnAction(event -> {
            gridPane.getChildren().clear();
            addGrid(gridPane);
            sourceSelected = false;
            destinationSelected = false;
            wallSelected = false;
            sourceNode = null;
            destinationNode = null;
        });


        Scene scene = new Scene(root);
        scene.getStylesheets().add("stylesheets/styles.css");
        stage.setScene(scene);

        root.getChildren().addAll(sourceButton, destinationButton, wallButton, pathButton, clearButton, gridPane);

        stage.show();
    }


}
