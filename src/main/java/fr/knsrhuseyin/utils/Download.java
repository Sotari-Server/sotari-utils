package fr.knsrhuseyin.utils;

import fr.knsrhuseyin.utils.component.colored.ColoredProgressBar;
import fr.knsrhuseyin.utils.knsrlogger.KnsrLogger;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class Download {

    String adresse;
    File path;
    String src;
    String extension;
    JLabel progressLabel;
    ColoredProgressBar progressBar;
    KnsrLogger logger;

    public Download(String adresse, String path, JLabel progressLabel, ColoredProgressBar progressBar, KnsrLogger logger) {
        this.adresse = adresse;
        this.path = new File(path);
        this.progressLabel = progressLabel;
        this.progressBar = progressBar;
        this.logger = logger;
    }

    public Download(String adresse, String path, String src, String extension, JLabel progressLabel, ColoredProgressBar progressBar, KnsrLogger logger) {
        this.adresse = adresse;
        this.path = new File(path);
        this.src = src;
        this.extension = extension;
        this.progressLabel = progressLabel;
        this.progressBar = progressBar;
        this.logger = logger;
    }

    public void download() {
        final DecimalFormat decimalFormat = new DecimalFormat("#.#");
        progressLabel.setText("Connection au serveur !");
        FileOutputStream fos = null;
        BufferedReader reader = null;

        this.progressBar.setValue(0);

        try
        {
            logger.info("Connection to the server !");
            URL url = new URL(adresse);

            URLConnection conn = url.openConnection();
            logger.info("Successful connection to the following address : " + adresse);

            String FileType = conn.getContentType();
            logger.info("File type : " + FileType);

            int fileLength = conn.getContentLength();

            if (fileLength == -1) {
                logger.err("Invalid file !");
                throw new IOException("Invalid file");
            }

            progressBar.setMaximum(fileLength);

            logger.info("Reading file...");
            InputStream in = conn.getInputStream();
            reader= new BufferedReader(new InputStreamReader(in));

            if (path == null)
            {
                progressLabel.setText("La destination n'existe pas !");
                logger.err("Destination null !");
                String fileName = url.getFile();
                fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
                path = new File(fileName);
                return;
            }

            byte[] buff = new byte[1024];
            fos  = new FileOutputStream(this.path);

            int n;
            while ((n=in.read(buff)) != -1)
            {
                fos.write(buff, 0, n);
                int value = this.progressBar.getValue()+n;
                double progress = (double) value / fileLength * 100.0d;
                this.progressBar.setValue(value);
                progressLabel.setText("Mise \u00e0 jour du launcher... " + decimalFormat.format(progress) + "%");
            }
            logger.info("The file was successfully installed on the path : " + path);
        }
        catch (Exception e)
        {
            logger.err("Error installing file : " + e);
            e.printStackTrace();
            progressLabel.setForeground(Color.RED);
            progressLabel.setText("Error !");
        }
        finally
        {
            try
            {
                fos.flush();
                fos.close();
            }
            catch (IOException e)
            {
                logger.err("Error while closing the connection : " + e);
                e.printStackTrace();
            }
            try
            {
                reader.close();
            }
            catch (Exception e)
            {
                logger.err("Error while closing the connection : " + e);
                e.printStackTrace();
            }
        }
    }

    public void extraireDossiers() {
        try
        {
            String fichierZip = path.getAbsolutePath();
            progressLabel.setText("Extraction des fichiers...");
            logger.info("Extracting Files...");
            int BUFFER = 2048;
            File fichier = new File(fichierZip);

            ZipFile zip = new ZipFile(fichier);

            // créer un répertoire avec le même nom que le fichier compressé
            String nouveauRep = fichierZip.substring(0, fichierZip.length() - (extension.length()+1));
            new File(nouveauRep).mkdir();
            Enumeration fichierZipEntries = zip.entries();

            // Parcouriri le contenu du fichier compressé
            while (fichierZipEntries.hasMoreElements())
            {
                // début du traitement de l'entrée courante
                ZipEntry entry = (ZipEntry) fichierZipEntries.nextElement();
                String currentEntry = entry.getName();
                File fichierDest = new File(nouveauRep, currentEntry);
                File destinationParent = fichierDest.getParentFile();

                // Création de répertoire container si nécessaire pour l'entrée courante
                destinationParent.mkdirs();

                if (!entry.isDirectory())
                {
                    BufferedInputStream is = new BufferedInputStream(zip.getInputStream(entry));
                    int currentByte;
                    byte data[] = new byte[BUFFER];
                    FileOutputStream fichier_out = new FileOutputStream(fichierDest);
                    BufferedOutputStream dest = new BufferedOutputStream(fichier_out, BUFFER);
                    while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
                        dest.write(data, 0, currentByte);
                    }
                    dest.flush();
                    dest.close();
                    is.close();
                }
                if (currentEntry.endsWith(extension))
                {
                    extraireDossiers();
                }
            }
        } catch (IOException err)
        {
            logger.err("Error while unzipping the zip : " + src);
            err.printStackTrace();
            return;
        }
    }

    public void zipDirectory() {
        final int BUFFER = 2048;
        String dossier_src = src;
        String fichier_zip_desc = path.getAbsolutePath();

        File node = new File(dossier_src);
        List<String> liste = new ArrayList();
        listeFichiers(node, liste, dossier_src);

        try{
            byte buffer[] = new byte[BUFFER];

            FileOutputStream fileos = new FileOutputStream(fichier_zip_desc);
            ZipOutputStream zipos = new ZipOutputStream(fileos);

            for(String file : liste){
                ZipEntry zipentry= new ZipEntry(file);
                zipos.putNextEntry(zipentry);
                FileInputStream in =
                        new FileInputStream(dossier_src + File.separator + file);

                int lec;
                while ((lec = in.read(buffer)) > 0) {
                    zipos.write(buffer, 0, lec);
                }

                in.close();
                System.out.println("Fichier "+file+" ajouté");
            }

            zipos.closeEntry();
            zipos.close();

            System.out.println("Fichier compressé créé!");

        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public static List listeFichiers(File node, List<String> liste, String dossier_src){

        //ajouter les fichiers
        if(node.isFile()){
            String file = node.getAbsoluteFile().toString();
            String chemin = file.substring(dossier_src.length()+1, file.length());
            liste.add(chemin);
        }

        if(node.isDirectory()){
            String[] sousFichier = node.list();
            for(String filename : sousFichier){
                listeFichiers(new File(node, filename), liste, dossier_src);
            }
        }
        return liste;
    }

    public void deleteFile() {
        try {
            Files.delete(path.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public File getPath() {
        return path;
    }

    public KnsrLogger getLogger() {
        return logger;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getExtension() {
        return extension;
    }

    public String getSrc() {
        return src;
    }

    public ColoredProgressBar getProgressBar() {
        return progressBar;
    }

    public JLabel getProgressLabel() {
        return progressLabel;
    }
}
