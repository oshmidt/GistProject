package com.oshmidt;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.egit.github.core.Gist;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author oshmidt Test for GistLocalFileManager.class
 */
public class GistLocalFileManagerTest {
	
    private final int COUNT = 5;
    
	
	public GistLocalFileManagerTest() {
		
	}
    
    private GistLocalFileManager repository;
    
    @BeforeMethod
    public void before() {
        repository = new GistLocalFileManager();
        
    }

    @Test
    public void testRepoPath() {
        
        String path = "local/Path";
        repository.setRepoPath(path);
        assertEquals(path, repository.getRepoPath());
        repository.loadDefaultRepoPath();
        assertNotNull(repository.getRepoPath());
    }

    @Test
    public void testReadFakeGists() {
        String path = "fake/Path";
        repository.setRepoPath(path);
        assertEquals(null, repository.readGists());
    }
    
    
    @Test
    public void testReadGists() throws IOException, ClassNotFoundException {
      
        File[] files = new File[COUNT];
        for (int i = 0; i < COUNT; i++) { 
            File f = mock(File.class);
            when(f.isFile()).thenReturn(true);
            when(f.getName()).thenReturn(Integer.toString(i) + ".gist");
            files[i] = f;
        }
        
        File file = mock(File.class);
        when(file.exists()).thenReturn(true);
        when(file.getName()).thenReturn("filename.gist");
        when(file.listFiles((FileFilter) any())).thenReturn(files);
        
        Gist gist = mock(Gist.class);
        when(gist.getDescription()).thenReturn("gist");
        
        ObjectInputStream objectInputStream = mock(ObjectInputStream.class);
        
        ObjectInputStreamFactory oisf = mock(ObjectInputStreamFactory.class);
        when(oisf.objectInputStreamFactory(any(File.class))).thenReturn(objectInputStream);
        when(oisf.readObject(any(ObjectInputStream.class))).thenReturn(gist);
        
  //      FileInputStream fileInputStreamMock = mock(FileInputStream.class);
        
        repository = new GistLocalFileManager(oisf);
 
        GistLocalFileManager spy = spy(repository);

        when(spy.gistFileFactory(anyString())).thenReturn(file);
      //  when(spy.fileInputStreamFactory(any(File.class))).thenReturn(fileInputStreamMock);
       // when(spy.objectInputStreamFactory((FileInputStream) Matchers.anyObject())).thenReturn(objectInputStreamMock);
    

        assertEquals(COUNT, spy.readGists().toArray().length);

        
    }
    
/*    public List<Gist> readGists() {
        ArrayList<Gist> gists = new ArrayList<Gist>();
        preparePath();
        gistFolder = new File(getRepoPath());
        if (!gistFolder.exists()) {
            return null;
        }
        FileFilter filter = createGistFilter();
        for (File gst : gistFolder.listFiles(filter)) {
            logger.info(gst.getName());
            gists.add(deserializeGist(gst));
        }
        return gists;
    }*/
    
    
    
 /*   @Test
    @Deprecated
    public void readFiles() {
        assertNotNull(repository.readFiles());     
    }*/
    
    
   /* @Test
    public void testWriteGists() {
        GistLocalFileManager gf = new GistLocalFileManager();
        FileOutputStream fileOutputStream = mock(FileOutputStream.class);
        gf.setFileOutputStream(fileOutputStream);
        List<Gist> list = new ArrayList<Gist>();
        for (int i = 0; i < 5; i++) {
            list.add(new Gist());
        }
             
        gf.writeGists(list);
    }*/

    /*
     * @Test public void testReadGists() throws InstantiationException,
     * IllegalAccessException, NoSuchFieldException, SecurityException,
     * ClassNotFoundException, IOException {
     * 
     * List<GistFile> fileList = new ArrayList<GistFile>(); for (int i = 0; i <
     * 5; i++) { fileList.add(new GistFile()); }
     * 
     * ObjectInputStream oin = mock(ObjectInputStream.class);
     * when(oin.readObject()).thenReturn(new Gist());
     * 
     * File gistFolder = mock(File.class);
     * when(gistFolder.exists()).thenReturn(true);
     * when(gistFolder.listFiles()).thenReturn((File[]) fileList.toArray());
     * 
     * Class<?> c = GistLocalFileManager.class; Object obj = c.newInstance();
     * GistLocalFileManager test = (GistLocalFileManager) obj;
     * 
     * Field f = test.getClass().getDeclaredField("gistFolder");
     * f.setAccessible(true); f.set(test, gistFolder);
     * 
     * f = test.getClass().getDeclaredField("oin"); f.setAccessible(true);
     * f.set(test, oin);
     * 
     * assertNotNull(test.readGists());
     * 
     * }
     */

    /*
     * @Test public void testReadFiles() { GistLocalFileManager gf = new
     * GistLocalFileManager(); String path = "fake/Path"; gf.setRepoPath(path);
     * assertEquals(null, gf.readFiles()); }
     * 
     * 
     * @Test public void testWriteGists() { GistLocalFileManager gf = new
     * GistLocalFileManager(); List<Gist> gists = new ArrayList<Gist>(); for
     * (int i = 0; i < 5; i++){ Gist g = new Gist();
     * 
     * g.setId(String.valueOf(i)); gists.add(g);
     * 
     * gf.writeFiles(gists); gf.writeGists(gists); }
     */

}
