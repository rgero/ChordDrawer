package net.roymond.ChordDrawer;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * DistanceTree
 *  Using a BST I can search for the closest points.
 * Created by Roymond on 2/9/2017.
 */
class DistanceTree {

    private class Node
    {
        private Point point;
        private double distance;
        private Node left, right;

        Node(Point p, double d){
            this.point = p;
            this.distance = d;
        }
    }

    private Node root;

    void put(Point p, double distance){
        root = put(root, p, distance);
    }

    private Node put(Node x, Point p, double d){
        if (x==null) return new Node(p, d);
        if ( d < x.distance ){
            x.left = put(x.left, p, d);
        } else if ( d > x.distance ){
            x.right = put(x.right, p,d);
        } else {
            x.distance = d;
        }
        return x;
    }

    List<Point> points(){
        List<Point> listPoints = new ArrayList<>();
        inorder(root, listPoints);
        return listPoints;
    }

    private void inorder(Node x, List<Point> p){
        if ( x == null ) return;
        inorder(x.left, p);
        p.add(x.point);
        inorder(x.right, p);
    }


}
