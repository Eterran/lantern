/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment.ds;

/**
 *
 * @author den51
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.io.*;

public class test {
    public static void main(String[] args)throws SQLException,IOException {
        LoginRegister LR=new LoginRegister();
       int choice= LR.choose();
       switch(choice){
           case 1:
               LR.login();
               break;
           case 2:
               LR.register();
               break;
           default: 
               System.out.println("Invalid number");
               break;
       }
       Database db=new Database();
       GlobalLeaderBoard glb=new GlobalLeaderBoard();
       try{
           System.out.println();
       glb.loadGlobal(db.connectionDatabase());}
       
       catch(IOException e){
       e.printStackTrace();
       }
       
       catch(SQLException e){
       e.printStackTrace();}
       
       
    }

}