package recommend;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

/**
 * HDFS�ļ�����
 * @author youxingzhi
 *
 */
public class HDFSFile {
	Configuration conf = new Configuration();
	private FileSystem hdfs;
	/**
	 * ���췽��
	 * @param hdfsPath
	 * @throws IOException
	 */
	public HDFSFile(Path hdfsPath) throws IOException{
		hdfs = hdfsPath.getFileSystem(conf);
	}
	/**
	 * ����Ŀ¼
	 * @param path
	 * @throws IOException
	 */
	public void mkDir(Path path) throws IOException{		
		hdfs.mkdirs(path);
	}
	/**
	 * �ϴ��ļ�
	 * @param src
	 * @param dst
	 * @throws IOException
	 */
	public void copyLocalToHdfs(Path src,Path dst) throws IOException{
		hdfs.copyFromLocalFile(src, dst);
	}
	/**
	 * ɾ���ļ�
	 * @param path
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public void delFile(Path path) throws IOException{
		hdfs.delete(path);
	}
	/**
	 * ��ȡ�ļ�����
	 * @param path
	 * @throws IOException
	 */
	public void readFile(Path path) throws IOException{
		//��ȡ�ļ���Ϣ
		FileStatus filestatus = hdfs.getFileStatus(path);
		//FS��������
		FSDataInputStream in = hdfs.open(path);
		//��Hadoop��IOUtils���߷�����������ļ���ָ���ֽڸ��Ƶ���׼�������
		IOUtils.copyBytes(in,System.out,(int)filestatus.getLen(),false);
		System.out.println();
	}
	/**
	 * �õ��ļ����޸�ʱ��
	 * @param path
	 * @throws IOException
	 */
	public void getModifyTime(Path path) throws IOException{
		FileStatus files[] = hdfs.listStatus(path);
		for(FileStatus file: files){
			//time = file.getModificationTime().
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String date = sdf.format(file.getModificationTime());
			System.out.println(file.getPath()+"\t"+date);
		}
	}
	/**
	 * ��hdfs�ϴ����ļ���д������
	 * @param path
	 * @param content
	 * @throws IOException
	 */
	public void writeFile(Path path,String content) throws IOException{
		FSDataOutputStream os = hdfs.create(path);
		//��utf-8�ĸ�ʽд���ļ�
		os.write(content.getBytes("UTF-8"));
		os.close();
	}
	/**
	 * �г�ĳһ·�������е��ļ�
	 * @param path
	 * @throws IOException
	 */
	public void listFiles(Path path) throws IOException{
		hdfs = path.getFileSystem(conf);
		FileStatus files[] = hdfs.listStatus(path);
		int listlength=files.length;  
        for (int i=0 ;i<listlength ;i++){  
            if (files[i].isDirectory() == false) {  
                System.out.println("filename:"  
                		+ files[i].getPath()  + "\tsize:"  
                        + files[i].getLen());  
            } else {  
                Path newpath = new Path(files[i].getPath().toString());  
                listFiles(newpath);  
            }  
        }  
	}
	public static void main (String arg[]) throws Exception{
		//
		HDFSFile file = new HDFSFile(new Path("hdfs://master:9000/test/"));
		Path dst = new Path("hdfs://master:9000/test/");
		Path src = new Path("/home/youxingzhi/hello.txt");
		//����Ŀ¼
		//file.mkDir(dst);
		//�ϴ������ļ���Hdfs
		//file.copyLocalToHdfs(src, dst);
		//��ȡ�ļ�
		//file.readFile(new Path(dst+"/test.txt"));
		//����޸�ʱ��
		//file.getModifyTime(new Path(dst+"/hello.txt"));
		//ɾ���ļ���Ŀ¼
		//file.myDelFile(dst);
		//�½��ļ�����д��
		//file.writeFile(new Path(dst+"/test.txt"), "��Ҫȥ����");
		//��ȡĿ¼�������ļ�
		//file.listFiles(new Path("hdfs://master:9000/"));
	}
}
