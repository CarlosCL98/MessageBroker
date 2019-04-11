package edu.eci.arsw.collabpaint.persistence.impl;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import edu.eci.arsw.collabpaint.model.Point;
import edu.eci.arsw.collabpaint.persistence.CollabPaintPersistence;

@Service
public class CollabPaintPersistenceImpl implements CollabPaintPersistence {

    private ConcurrentHashMap<Integer, ArrayBlockingQueue<Point>> points = new ConcurrentHashMap<>();
    private ArrayBlockingQueue<Point> pointsValue = new ArrayBlockingQueue<>(100);

    @Override
    public void savePoint(int numdibujo, Point pt) {
        System.out.println(points.size());
        if (!points.contains(numdibujo)) {
            pointsValue.add(pt);
            points.put(numdibujo, pointsValue);
        } else {
            pointsValue = points.get(numdibujo);
            pointsValue.add(pt);
            points.replace(numdibujo, pointsValue);
        }
    }

    @Override
    public ArrayBlockingQueue<Point> getPoints(int numdibujo) {
        return points.get(numdibujo);
    }

    @Override
    public void resetPolygon(int numdibujo) {
        pointsValue.clear();
        points.replace(numdibujo, pointsValue);
    }

}
