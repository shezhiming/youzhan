package recommend;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;

/**
 * ������Ʒ��Эͬ�Ƽ�ϵͳ
 */
public class Recommend {
	//HDFS�ļ�·��
	public static final String HDFS = "hdfs://10.104.44.138:9000";
	//MapReduce�ָ��Ϊ\t��,
    public static final Pattern DELIMITER = Pattern.compile("[\t,]");
    //��ں���
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
    	Map<String, String> path = new HashMap<String, String>();
    	//��������·��
    	path.put("data", "/home/hadoop/input/graded1.csv");
    	//����1���������·��
        path.put("Step1Input", HDFS + "/recommend");
        path.put("Step1Output", path.get("Step1Input") + "/step1");
        //����2���������·��
        path.put("Step2Input", path.get("Step1Output"));
        path.put("Step2Output", path.get("Step1Input") + "/step2");
        //����3_1���������·��
        path.put("Step3Input1", path.get("Step1Output"));
        path.put("Step3Output1", path.get("Step1Input") + "/step3_1");
        //����3_2���������·��
        path.put("Step3Input2", path.get("Step2Output"));
        path.put("Step3Output2", path.get("Step1Input") + "/step3_2");
        //����4���������·��
        path.put("Step4_1Input1", path.get("Step3Output1"));
        path.put("Step4_1Input2", path.get("Step3Output2"));
        path.put("Step4_1Output", path.get("Step1Input") + "/step4_1");      
        path.put("Step4_2Input", path.get("Step4_1Output"));
        path.put("Step4_2Output", path.get("Step1Input") + "/step4_2");
        //����5���������·��
        path.put("Step5Input1", path.get("Step4_2Output"));
        path.put("Step5Input2", path.get("Step1Input")+"/graded1.csv");
        path.put("Step5Output", path.get("Step1Input") + "/step5");
        

        Step1.run(path);
        Step2.run(path);
        Step3.run1(path);
        Step3.run2(path);   
        Step4_Update.run(path);
        Step4_Update2.run(path);
        Step5.run(path);
        
        //���������ն�
        HDFSFile hdfs = new HDFSFile(new Path(HDFS));
        //Step1��������
        //System.out.println(path.get("Step1Output")+"/part-r-00000");
        //hdfs.readFile(new Path(path.get("Step1Output")+"/part-r-00000"));
        //Step2��������
        //System.out.println(path.get("Step2Output")+"/part-r-00000");
        //hdfs.readFile(new Path(path.get("Step2Output")+"/part-r-00000"));
        //Step3_1��������
        //System.out.println(path.get("Step3Output1")+"/part-r-00000");
        //hdfs.readFile(new Path(path.get("Step3Output1")+"/part-r-00000"));
        //Step3_2��������
        //System.out.println(path.get("Step3Output2")+"/part-r-00000");
        //hdfs.readFile(new Path(path.get("Step3Output2")+"/part-r-00000"));
        //Step4_1��������
        //System.out.println(path.get("Step4_1Output")+"/part-r-00000");
        //hdfs.readFile(new Path(path.get("Step4_1Output")+"/part-r-00000"));
        //System.exit(0);
        //Step4_2��������
        //System.out.println(path.get("Step4_2Output")+"/part-r-00000");
        //hdfs.readFile(new Path(path.get("Step4_2Output")+"/part-r-00000"));
        //Step5��������
        System.out.println(path.get("Step5Output")+"/part-r-00000");
        hdfs.readFile(new Path(path.get("Step5Output")+"/part-r-00000"));
        
        System.exit(0);
    
    }
    public static Configuration config() {
    	Configuration conf = new Configuration();
        return conf;
    }
}
