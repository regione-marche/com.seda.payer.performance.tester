package com.seda.payer.performance.tester;

import java.net.URL;

import com.seda.payer.performance.tester.config.WsUrls;
import com.seda.payer.pgec.webservice.commons.dati.RecuperaProvinceConvenzionateRequest;
import com.seda.payer.pgec.webservice.commons.source.CommonsSOAPBindingStub;
import com.seda.payer.pgec.webservice.commons.source.CommonsServiceLocator;


public class Main {

	private static String dbSchemaCodSocieta = "";
	private static int readTimeOut = 0;
	private static final String DBSCHEMACODSOCIETA = "dbSchemaCodSocieta";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			
			if (args == null || args.length <= 0) {
				System.err.println("Usage:  com.seda.payer.performance.tester.Main -s <dbSchemaCodSocieta> [-t <timeout in millisecondi>]");
				System.err.println("Options:");
				System.err.println("-s: codice società per connessione dinamica al DB");
				System.err.println("-t: Timeout in millisecondi"); 
				System.exit(1);
			}
			
			if (!parseArgs(args))
				System.exit(1);
			
			CommonsServiceLocator serviceLocator = new CommonsServiceLocator();
			
			CommonsSOAPBindingStub commons = (CommonsSOAPBindingStub)serviceLocator.getCommonsPort(new URL(WsUrls.wsCommonsUrl.format()));
			if (readTimeOut > 0)
				commons.setTimeout(readTimeOut);
			
			commons.clearHeaders();
			commons.setHeader("", DBSCHEMACODSOCIETA,dbSchemaCodSocieta);	
			
			RecuperaProvinceConvenzionateRequest in=new RecuperaProvinceConvenzionateRequest();
			
			long startTimeMillis = System.currentTimeMillis();
			
			//RecuperaProvinceConvenzionateResponse out;
			commons.recuperaProvinceConvenzionate(in);
			//Thread.sleep(1000);
			
			long endTimeMillis = System.currentTimeMillis();
			
			//System.out.println(getSeconds(endTimeMillis,startTimeMillis));
			System.out.println(endTimeMillis-startTimeMillis);
			
		} catch (Exception e) {
			System.out.println(-1);
		}

	}
	
	
	
	public static int getSeconds(long startTimeMillis, long endTimeMillis) { 
		    
		try { 
		            
			//String strDate1 = "2007/04/15 12:35:05"; 
			//String strDate2 = "2009/08/02 20:45:07"; 
			 
		            
			//SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); 
			//fmt.setLenient(false); 
			            
			// Parses the two strings. 
			//Date d1 = fmt.parse(strDate1); 
			//Date d2 = fmt.parse(strDate2); 
			            
			// Calculates the difference in milliseconds. 
			long millisDiff = startTimeMillis - endTimeMillis; 
			            
			// Calculates days/hours/minutes/seconds. 
			int seconds = (int) (millisDiff / 1000 % 60); 
			//int minutes = (int) (millisDiff / 60000 % 60); 
			//int hours = (int) (millisDiff / 3600000 % 24); 
			//int days = (int) (millisDiff / 86400000); 
			 
		            
			return seconds; 
		        
		} catch (Exception e) { 
			return -1;
		} 
		    
	} 
	
	public static boolean parseArgs(String[] args) {
		boolean ret = true;
		boolean bOk = true;
		for (int i = 0; i < args.length && bOk; i++) {
			if (args[i].length() > 1 && (args[i].charAt(0) == '-')) {
				if (i == args.length - 1) {
					ret = false;
					break;
				}
				switch (args[i].toLowerCase().charAt(1)) {
					
					case 's':
						dbSchemaCodSocieta = args[++i];
						if (dbSchemaCodSocieta == null || dbSchemaCodSocieta.trim().length() == 0 
								|| dbSchemaCodSocieta.trim().charAt(0) == '-')
						{
							System.err.println("parametro -s mancante. Parametro obbligatorio");
							ret = bOk = false;
						}
						break;
					case 't':
						String t_readTimeOut = args[++i];
						try { 
							readTimeOut = Integer.parseInt(t_readTimeOut);
						} catch (Exception e) {
							System.err.println("parametro -t valore non permesso, formato in millisecondi");
							ret = bOk = false;
						}
						break;
					default:
						ret = false;
					break;
				}
			}
		}
		
		if (dbSchemaCodSocieta == null || dbSchemaCodSocieta.length() == 0)
		{
			System.err.println("parametro -s obbligatorio");
			ret = false;
		}
		
		return ret;
	}


}
