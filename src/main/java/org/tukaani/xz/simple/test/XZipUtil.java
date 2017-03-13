package org.tukaani.xz.simple.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.tukaani.xz.LZMA2Options;
import org.tukaani.xz.XZOutputStream;
/**
 * 压缩文件(不支持文件夹归档)为xz
 * 测试:PC端 cpu 50%
 * @author zeyi.chen
 *
 */
public class XZipUtil {
	private static void zipFile(File fileToZip, String zippath, XZOutputStream xzos) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(fileToZip);
			byte[] b = new byte[1024];
			int size = -1;
			while ((size = fis.read(b)) != -1)
				xzos.write(b, 0, size);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (fis != null) fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private static void zipFolder(File folder, String zippath, XZOutputStream xzos) {
		String[] subFn = folder.list();

		for (int i = 0; i < subFn.length; i++) {
			File subFile = new File(folder.getPath() + "/" + subFn[i]);
			if (subFile.isFile()) {
				zipFile(subFile, zippath + "/" + subFile.getName(), xzos);
			} else
				zipFolder(subFile, zippath + "/" + subFile.getName(), xzos);
		}
	}
	private static void zip(String[] files, XZOutputStream xzos) throws Exception {
		String[] arrayOfString = files;
		int j = files.length;
		for (int i = 0; i < j; i++) {
			String file = arrayOfString[i];
			File fileStart = new File(file);
			if (fileStart.isFile()) {
				zipFile(fileStart, fileStart.getName(), xzos);
			} else{
				throw new Exception("xz压缩对象必须是文件");
			}
		}
	}
	
	public static boolean zip(String resource, String target) {
		return zip(new String[] { resource }, target);
	}

	public static boolean zip(String[] resource, String target) {
		XZOutputStream xzos = null;
		try {
			File file = new File(target);
			if (!(file = new File(file.getParent())).exists()) {
				file.mkdirs();
			}
			
			xzos = new XZOutputStream(new FileOutputStream(target),new LZMA2Options());
			zip(resource, xzos);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (xzos != null)
				try {
					xzos.flush();
					xzos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return true;
	}

	public static void main(String[] args) {
		//Getting an output stream to compress with LZMA2 using the default settings and the default integrity check type (CRC64):
		zip(new String[] { "E:/BackUp/test/TASKFILE_BAK_20150602.tar" }, "E:/BackUp/test/TASKFILE_BAK_20150602.tar.xz");
			
//			try {
//				 File file = new File("D://FTS_CN0018017_20151117_XIB.PER.COSIG.VOU_ICOP20151117141032_3000001865.OUT"); 
//				 FileInputStream fis = new FileInputStream(file);
//				 FileOutputStream outfile = new FileOutputStream("D://foo.xz");
//				 XZOutputStream outxz = new XZOutputStream(outfile, new LZMA2Options());
//				 byte[] b = new byte[1024];
//					int size = -1;
//
//					while ((size = fis.read(b)) != -1)
//						outxz.write(b, 0, size);
//				 outxz.flush();
//				 outxz.close();
//				 fis.close();
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			 
			//Using the preset level 8 for LZMA2 (the default is 6) and SHA-256 instead of CRC64 for integrity checking:

//			 XZOutputStream outxz = new XZOutputStream(outfile, new LZMA2Options(8),
//			                                           XZ.CHECK_SHA256);
//			 
			//Using the x86 BCJ filter together with LZMA2 to compress x86 executables and printing the memory usage information before creating the XZOutputStream:

//			 X86Options x86 = new X86Options();
//			 LZMA2Options lzma2 = new LZMA2Options();
//			 FilterOptions[] options = { x86, lzma2 };
//			 System.out.println("Encoder memory usage: "
//			                    + FilterOptions.getEncoderMemoryUsage(options)
//			                    + " KiB");
//			 System.out.println("Decoder memory usage: "
//			                    + FilterOptions.getDecoderMemoryUsage(options)
//			                    + " KiB");
//			 XZOutputStream outxz = new XZOutputStream(outfile, options);

	}

}
