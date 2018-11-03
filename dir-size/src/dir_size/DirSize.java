package dir_size;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Stream;
import java.util.ArrayList;

public class DirSize {
	private Path rootDir = Paths.get("/home");
	
	public static void main(String[] args) {
		//getSubDirsPassiveIterator(rootDir);
		//System.out.println(getSubDirs(rootDir));

		if (args.length <= 0 || args[0] == null) 
			System.exit(1);
		
		DirSize dirSize = new DirSize(args[0]);
		SubDir subDir = dirSize.getElemsOfParentDir(dirSize.rootDir);
		long rootSize = 0;
		for(Path dir : subDir.dirs) {
			long size = dirSize.calcSize(dir);
			rootSize += size;
			System.out.println(size + " - " + dir.toString());
			//TODO sort by size, format numbers, format output text with even spacing.
		}
		for(Path file : subDir.files) {
			try {
				long currFileSize = Files.size(file);
				System.out.println(currFileSize + " - " + file.toString());
				rootSize+=currFileSize;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(rootSize + " - .");
	}
	
	public DirSize(String path) {
		rootDir = Paths.get(path);
	}
	
	class SubDir {
		List<Path> dirs = new ArrayList<>();
		List<Path> files = new ArrayList<>();
	}
	
	public SubDir getElemsOfParentDir(Path rootDir) {
		SubDir subDir = new SubDir();
		try(Stream<Path> input = Files.list(rootDir)){
			input.forEach(el ->{
				if(el.toFile().isDirectory())
					subDir.dirs.add(el);
				else if (el.toFile().isFile())
					subDir.files.add(el);
			});
		}catch (IOException e) {
			System.out.println(e.toString());
		}
		return subDir;
	}
	
	public long calcSize(Path p) {
		//TODO Calculates dir size as a sum of file sizes, 
		//     but does not add the size of dir itself, 
		//     which seems to be 4096 bytes in Linux.
		SubDir subDir = getElemsOfParentDir(p);
		long size = 0;
		for(Path dir : subDir.dirs) {
			long currDirSize = calcSize(dir);
			System.out.println(currDirSize + " - " + dir.toString());
			size += currDirSize;
		}
		for(Path file : subDir.files) {
			try {
				long currFileSize = Files.size(file);
				System.out.println(currFileSize + " - " + file.toString());
				size += currFileSize;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return size;
	}
	
	/*public static Long getSubDirs(Path p) {
		List<Path> dirs = new ArrayList<>();
		long currentDirSize = 0;
		
		if(p.toFile().isDirectory()) {
			try (Stream<Path> input =Files.list(p)){
				input.forEach(el -> dirs.add(el));
			}catch(IOException e) {
				e.printStackTrace();
			}
				for(Path item : dirs) {
					if(item.toFile().isDirectory()) {
						System.out.println(getSubDirs(item)+" - "+item.toString());
					}
					else if(item.toFile().isFile()) {
						try {
							System.out.println(Files.size(item)+" - "+ item.toString());
							currentDirSize+=Files.size(item);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
		}
		return currentDirSize;
	}*/
	
/*	public static void getSubDirsPassiveIterator(Path p) {
		if(Files.isDirectory(p, LinkOption.NOFOLLOW_LINKS)) {
			try {
				Files.list(p).forEach(d -> {
									try {
										getSubDirsPassiveIterator(d);
										System.out.println(Files.size(d)+" - "+d.toString());
									} catch (IOException e) {
										e.printStackTrace();
									}
									});
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}*/
}
