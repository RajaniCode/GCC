// Java 16
// OpenJDK 16
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/JDK16.0.2/jdk-16.0.2.jdk/Contents/Home/bin/":$PATH

// Java16
// javac --add-modules jdk.incubator.foreign ForeignLinkerAPIIncubator.java
// java --add-modules jdk.incubator.foreign -Dforeign.restricted=permit ForeignLinkerAPIIncubator

import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.FunctionDescriptor;
// Removed in JDK 17
import jdk.incubator.foreign.LibraryLookup;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

import java.nio.file.Path;
import java.util.Optional;

public class ForeignLinkerAPIIncubator {

  public static void main(String[] args) throws Throwable {

      String userDir = System.getProperty("user.dir");

      String fileName = "msys64-mingw64.so";

      String dirFile = userDir + "/" + fileName;

      System.out.println(dirFile);

      Path pathOf = Path.of(dirFile);

      LibraryLookup libraryLookup = LibraryLookup.ofPath(pathOf);

      Optional<LibraryLookup.Symbol> optionalLibraryLookupSymbol = libraryLookup.lookup("hola");
  
      if (optionalLibraryLookupSymbol.isPresent()) {

        LibraryLookup.Symbol libraryLookupSymbol = optionalLibraryLookupSymbol.get();

	FunctionDescriptor descriptorFunction = FunctionDescriptor.ofVoid();
         
	// return type, parameter type(s)
        MethodType typeMethod = MethodType.methodType(Void.TYPE);

        MethodHandle handleMethod = CLinker.getInstance().downcallHandle(
            libraryLookupSymbol.address(),
 	    typeMethod,
	    descriptorFunction);

        handleMethod.invokeExact();
    }
  }
}