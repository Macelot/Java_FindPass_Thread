/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.empresa.findpass_thread;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 *
 * @author marce
 */
public class ThreadLoadFile implements Runnable{
    private boolean exit = false;
    private String passwordTarget;
    private static String file;
    private static boolean found = false;
    private static int debugLevel = 0;
    
    
    ArrayList<String> passwords;
    
    int total;
    
    public ThreadLoadFile (String p){
        //this.passwords = new ArrayList<>();
        //this.passwordTarget=p;
        this.setPasswords(new ArrayList<>());
        //this.setPasswordTarget(p);
        passwordTarget=p;
    }
    
    public ArrayList<String> getPasswords() {
        return passwords;
    }

    public void setPasswords(ArrayList<String> pass) {
        this.passwords = pass;
    }
    
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public synchronized  String getPasswordTarget() {
        return passwordTarget;
    }

    public synchronized  void setPasswordTarget(String passwordTarget) {
        this.passwordTarget = passwordTarget;
    }



    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public static int getDebugLevel() {
        return debugLevel;
    }

    public static void setDebugLevel(int debugLevel) {
        ThreadLoadFile.debugLevel = debugLevel;
    }
    
    
    
    
    @Override
    public void run() {
        while (!this.exit) {
            try {
                if(this.debugLevel==1){
                    System.out.println("Init thread");
                }
                long inicio = System.currentTimeMillis();            
                FileReader fr = new FileReader(file);
                BufferedReader bf = new BufferedReader(fr);
                String linha = bf.readLine(); 
                this.found=false;
                while(linha!=null){    
                    passwords.add(linha);
                    if(this.debugLevel==1){
                        System.out.println("Search "+passwordTarget);
                        System.out.println("Read "+linha);
                    }
                    if(linha.equals(passwordTarget)){
                        System.out.print("Password [ "+passwordTarget+" ]");
                        System.out.println("---------Found---------");
                        this.found=true;
                        this.exit=true;
                        break;
                    }
                    linha=bf.readLine(); 
                } 
                
                if(!this.found){
                    System.out.print("Password [ "+this.passwordTarget+" ]");
                    System.out.println("!!!!!!!!!Not Found!!!!!!!!!");
                }
                this.exit=true;
                
                this.setTotal(this.getPasswords().size());
                long fim = System.currentTimeMillis(); 
                System.out.println("\n Time lapsed : " + ((fim - inicio)/1000) + " seconds.");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Erro "+e.getMessage());
            }  
        }
    }
    void stop(){
        this.exit = true;
    }
}

