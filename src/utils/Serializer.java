/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import views.FXMLDocumentController;

/**
 * 
 * @author User
 */
public class Serializer {
    public static void save(Object o){
        File f = new File(o.toString() + ".object");
        try (ObjectOutputStream stream = new ObjectOutputStream (new FileOutputStream (f));) {
            stream.writeObject (o);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static <T> T LoadData(Class<T> className) throws InstantiationException, IllegalAccessException {
        File f = new File(className.toString() + ".object");
        try (ObjectInputStream stream = new ObjectInputStream (new FileInputStream (f));) {
            T object = (T) stream.readObject();
            return object;
        } catch (ClassNotFoundException e) {
            System.out.println ("Impossible de lire l'objet : " + e.getMessage());
            return className.newInstance();
        } catch (IOException e2) {
            System.out.println ("Erreur lors de l'écriture : " + e2.getMessage());
            return className.newInstance();
        }
    }
}
