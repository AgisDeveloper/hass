/*
*File: agis.ps.file.AlignmentFileReader.java
*User: mqin
*Email: mqin@ymail.com
*Date: 2017年1月11日
*/
package agis.ps.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import agis.ps.link.MRecord;
import agis.ps.util.MRecordValidator;
import agis.ps.util.Parameter;
import agis.ps.util.Strand;

public abstract class AlignmentFileReader {
	private final static Logger logger = LoggerFactory.getLogger(AlignmentFileReader.class);
	private String algFile;
	protected Parameter paras = null;
	private Map<String, Integer> cntCovs = null;
	private Map<String, List<MRecord>> records = null;
	private Map<String, Integer> cntLens = null;
	private List<List<MRecord>> listRecords = null;
	private Map<String, Integer> samCntLens = null;
	
	public AlignmentFileReader(Parameter paras)
	{
		this.algFile = paras.getAlgFile();
		this.paras = paras;
	}
	
//	public Map<String, List<MRecord>> read()
	public List<List<MRecord>> read()
	{
		long start = System.currentTimeMillis();
		if(records == null)
			records = new HashMap<String, List<MRecord>>();
		if(cntCovs == null)
			cntCovs = new HashMap<String, Integer>();
		if(cntLens == null)
			cntLens = new HashMap<String, Integer>();
		if(listRecords == null)
			listRecords = new ArrayList<List<MRecord>>();
		if(samCntLens == null)
			samCntLens = new HashMap<String, Integer>();
		records.clear();
		cntCovs.clear();
		cntLens.clear();
		listRecords.clear();
		samCntLens.clear();
		File file = null;
		FileReader fr = null;
		BufferedReader br = null;
		try
		{
			file = new File(algFile);
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String line = null;
			String qId = "";
			List<MRecord> rs = new ArrayList<MRecord>();
			while(true)
			{
				line = br.readLine();
				if(line == null)
					break;
				line = line.trim();
				String [] arrs = line.split("\\s+");
//				if(arrs[0].equals("PB8356"))
//					logger.debug("breakpoint");
				if(arrs[0].equals("qName"))
					continue;
				// for sam format to get reference length
				if(paras.getType().equalsIgnoreCase("sam") && arrs[0].startsWith("@"))
				{
					if(arrs[0].equalsIgnoreCase("@SQ"))
					{
						// seqeunce name 
						String [] names = arrs[1].split(":");
						String name = names[1];
						// sequence length
						String [] lengths = arrs[2].split(":");
						Integer len = Integer.valueOf(lengths[1]);
						if(!samCntLens.containsKey(name))
							samCntLens.put(name, len);
					}
					continue;
				}
				MRecord record = initMRecord(arrs);
				if(record == null)
					continue;
				if(paras.getType().equalsIgnoreCase("sam"))
				{
					int length = samCntLens.get(record.gettName());
					record.settLength(length);
				}
				if(!cntLens.containsKey(record.gettName()))
				{
					cntLens.put(record.gettName(), record.gettLength());
				}
				Map<String, Boolean> values = MRecordValidator.validate(record, paras);
				if(values.get("REPEAT"))
				{
					String tName = record.gettName();
					if(cntCovs.containsKey(tName))
					{
						int count = cntCovs.get(tName) + 1;
						cntCovs.replace(tName, count);
					} else
					{
						cntCovs.put(tName, 1);
					}
				}
				// hashmap
//				if(values.get("RECORD"))
//				{
//					String qName = record.getqName();
//					if(qName.equals(qId))
//					{
//						rs.add(record);
//					} else
//					{
//						if(rs.size() > 1)
//							records.put(qId, rs);
//						rs = new LinkedList<MRecord>();
//						rs.add(record);
//						qId = record.getqName();
//					}
//				}
				// arraylist
				if(values.get("RECORD"))
				{
					String qName = record.getqName();
					if(qName.equals(qId))
					{
						rs.add(record);
					} else
					{
						if(rs.size() > 1)
							listRecords.add(rs);
						rs = new LinkedList<MRecord>();
						rs.add(record);
						qId = record.getqName();
					}
				}
			}
			br.close();
		} catch(IOException e)
		{
			logger.error(this.getClass().getName() + "\t" + e.getMessage());;
		} catch(Exception e)
		{
			logger.error(this.getClass().getName() + "\t" + e.getMessage());
		} finally
		{
			try{
				if(br != null)
					br.close();
			} catch(Exception e)
			{
				logger.error(this.getClass().getName() + "\t" + e.getMessage());
			}
		}
		long end = System.currentTimeMillis();
//		logger.info("Valid Aligned Records: " + records.values().size());
		logger.info("Valid Aligned Records: " + listRecords.size());
		logger.info("Reading Aligned Records, erase time: " + (end - start) + " ms");
//		return records;
		return listRecords;
	}

	protected abstract MRecord initMRecord(String arrs []);
	
	public Map<String, Integer> getCntCoverages()
	{
		return this.cntCovs;
	}
	
	public Map<String, Integer> getCntLengths()
	{
		return this.cntLens;
	}
	
	public List<List<MRecord>> getListRecord()
	{
		return this.listRecords;
	}
//	// print in m4 format
//	private void pritnMRecord(MRecord record){
//		System.out.println(record.getqName() + "\t" + record.gettName() + "\t" + "-1000" + "\t" +
//				record.getIdentity() + "\t" + "0" + record.getqStart() + "\t" + record.getqEnd() + "\t" +
//				record.getqLength() + "\t" + (record.gettStart().equals(Strand.FORWARD)?"0":"1") + "\t" +
//				record.gettStart() + "\t" + record.gettEnd() + "\t" + record.gettLength() + "\t" + 255);
//	}
}


