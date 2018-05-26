package test;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;




/**
 * �õ��û���������
 *
 */
public class Step1{
	
	public static final Pattern DELIMITER = Pattern.compile("[\t,]");
	//HDFS�ļ�·��
	public static final String HDFS = "hdfs://10.104.44.138:9000";
	
    public static class Step1_ToItemPreMapper extends Mapper<Object, Text, IntWritable, Text> {
        private final static IntWritable k = new IntWritable();
        private final static Text v = new Text();

        @Override
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String[] tokens = DELIMITER.split(value.toString());
            int userID = Integer.parseInt(tokens[0]);
            String itemID = tokens[1];
            String pref = tokens[2];
            k.set(userID);
            v.set(itemID + ":" + pref);
            context.write(k, v);
        }
    }

    public static class Step1_ToUserVectorReducer extends Reducer <IntWritable, Text, IntWritable, Text> {
        private final static Text v = new Text();
        
        @Override
		protected void reduce(IntWritable key, Iterable<Text> values,
				Reducer<IntWritable, Text, IntWritable, Text>.Context context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
        	 StringBuilder sb = new StringBuilder();
             for (Text value:values) {
                 sb.append("," + value.toString());
             }
             v.set(sb.toString().replaceFirst(",", ""));
             context.write(key, v);
		}

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
    	//���������Ϣ
    	Configuration conf = new Configuration();
    	//�õ��������·��
        Path input = new Path(HDFS + "/test");
        Path output = new Path(HDFS + "/testop");
        //�������ļ��ϴ�����Ⱥ
        HDFSFile hdfs = new HDFSFile(new Path(HDFS));
        hdfs.delFile(input);
        hdfs.mkDir(input);
        hdfs.copyLocalToHdfs(new Path("/home/hadoop/input/small2.csv"), input);
        //������ҵ����
        Job job = Job.getInstance(conf,"Step1");
		job.setJarByClass(Step1.class);
		
		// 2. ����Mapper���Reducer��
		job.setMapperClass(Step1_ToItemPreMapper.class);
		job.setCombinerClass(Step1_ToUserVectorReducer.class);
		job.setReducerClass(Step1_ToUserVectorReducer.class);
		// 4. ���������� key �� value ������
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(Text.class);
		
		FileInputFormat.addInputPath(job,input);
		FileOutputFormat.setOutputPath(job,output);
		//������ҵ
		job.waitForCompletion(true);
    }

}