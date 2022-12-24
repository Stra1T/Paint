import acm.graphics.*;
import acm.program.GraphicsProgram;
import acmx.export.javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;


public class Paint extends GraphicsProgram {
    private JCheckBox delete;
    GOval bucket;
    private GOval pixel;
    private JSlider size;
    private JCheckBox drow;
    private JCheckBox drowLine;
    private GLine line;
    private JComboBox colors;
    private JButton clear;
    private JComboBox shapes;
    public void init(){
        delete = new JCheckBox("Delete");
        add(delete,NORTH);
        addMouseListeners();
        clear = new JButton("Clear");
        add(clear, NORTH);
        addActionListeners();
        clear.addActionListener(this);
        drowLine = new JCheckBox("Line");
        add(drowLine, NORTH);
        JLabel X = new JLabel("Size");
        add(X,NORTH);
        size = new JSlider(1,getWidth()/12,1);
        add(size,NORTH);
        drow = new JCheckBox("Draw");
        add(drow, NORTH);
        colors = new JComboBox();
        initColors();
        shapes = new JComboBox();
        initShapes();
        add(colors, NORTH);
        add(shapes, NORTH);
        colors.setSelectedItem("black");
        shapes.setSelectedItem("circle");
        addIcon();
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == clear) {
            getGCanvas().removeAll();
            add(bucket);
        }
    }


    private void initShapes(){
        shapes.addItem("circle");
        shapes.addItem("rectangle");
        shapes.addItem("triangle");
    }
    private void initColors(){
        colors.addItem("red");
        colors.addItem("green");
        colors.addItem("blue");
        colors.addItem("black");
        colors.addItem("orange");
    }
    private Color getCurrentColor() {
        String name = (String) colors.getSelectedItem();
        switch (name) {
            case "blue":
                return Color.BLUE;
            case "red":
                return Color.RED;
            case "orange":
                return Color.ORANGE;
            case "green":
                return Color.GREEN;
            case "black":
                return Color.BLACK;
            default:
                return Color.WHITE;
        }
    }
    private boolean drowSettings(){
        return !drowLine.isSelected() && drow.isSelected();
    }
    private boolean lineSettings(){
        return drowLine.isSelected() && !drow.isSelected();
    }
    private Boolean collorCangeSettings(){
        return !drowLine.isSelected() && !drow.isSelected();
    }
    private Boolean circleSettings(){
        return shapes.getSelectedItem() == "circle"  &&  !drowLine.isSelected() && !drow.isSelected();
    }
    private Boolean rectSettings(){
        return shapes.getSelectedItem() == "rectangle"  &&  !drowLine.isSelected() && !drow.isSelected();
    }
    private boolean deleteSettings(){
        return !drowLine.isSelected() && !drow.isSelected() && delete.isSelected();
    }
    private void addIcon(){
        bucket = new GOval(10,10);
        bucket.setFilled(true);
        bucket.setColor(getCurrentColor());
        add(bucket,0,0);
    }
    public void mouseMoved(MouseEvent e){
        bucket.setLocation(e.getX()+bucket.getHeight()/2,e.getY()-bucket.getHeight()/2);
        if(line != null && drowLine.isSelected()){
            line.setEndPoint(e.getX(),e.getY());
        }
    }
    public void mouseClicked(MouseEvent e){
        GObject obj = getElementAt(e.getX(),e.getY());
        if(obj != null && collorCangeSettings() && obj != pixel){
            obj.setColor(getCurrentColor());
            bucket.setColor(getCurrentColor());
        }
        if(line == null && lineSettings() && !deleteSettings()){
            bucket.setColor(getCurrentColor());
            line = new GLine (e.getX(),e.getY(),e.getX(),e.getY());
            line.setColor(getCurrentColor());
            add(line);
        }
        else if(lineSettings() && !deleteSettings()){
            line.setEndPoint(e.getX(), e.getY());
            line = null;
        }
    }
    public void mousePressed(MouseEvent e){
        GObject obj = getElementAt(e.getX(),e.getY());
        if(obj == null && circleSettings() && !deleteSettings()) {
            GOval  object = new GOval(size.getValue(),size.getValue());
            object.setFilled(true);
            object.setColor(getCurrentColor());
            add(object, e.getX() - object.getWidth() / 2, e.getY() - object.getHeight() / 2);
        }
        if(obj == null && rectSettings() && !deleteSettings()) {
            GRect object = new GRect(size.getValue(),size.getValue());
            object.setFilled(true);
            object.setColor(getCurrentColor());
            add(object, e.getX() - object.getWidth() / 2, e.getY() - object.getHeight() / 2);
        }
//        if(shapes.getSelectedItem() == "triangle" && obj == null) {
//            GPolygon object = new GPolygon();
//            object.addVertex(e.getX()-15, e.getY()+(Math.sqrt(30*30+15+15))/2);
//            object.addVertex(e.getX()+15, e.getY()+(Math.sqrt(30*30+15+15))/2);
//            object.addVertex(e.getX(), e.getY()-(Math.sqrt(30*30+15+15))/2);
//            object.setFilled(true);
//            object.setColor(getCurrentColor());
//            add(object);
//        }
    }

    public void mouseDragged(MouseEvent e){
        GObject obj = getElementAt(e.getX(),e.getY());
        bucket.setLocation(e.getX()+bucket.getHeight()/2,e.getY()-bucket.getHeight()/2);
        if(obj != null && collorCangeSettings() && obj != pixel){
            obj.setLocation(e.getX()- obj.getWidth()/2, e.getY()-obj.getHeight()/2);
        }
        if(drowSettings() && !deleteSettings()){
            bucket.setColor(getCurrentColor());
            pixel = new GOval(size.getValue(), size.getValue());
            pixel.setFilled(true);
            pixel.setColor(getCurrentColor());
            add(pixel, e.getX()-pixel.getWidth()/2,e.getY()-pixel.getHeight()/2);
        }
        if(deleteSettings()){
            remove(obj);
        }
    }



}

