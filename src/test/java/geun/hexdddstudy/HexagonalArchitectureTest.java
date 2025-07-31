package geun.hexdddstudy;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.library.Architectures;

@AnalyzeClasses(packages = "geun.hexdddstudy", importOptions = ImportOption.DoNotIncludeTests.class)
public class HexagonalArchitectureTest {
    @ArchTest
    void haxagonalArchitecture(JavaClasses classes) {
        Architectures.layeredArchitecture()
                .consideringAllDependencies()
                .layer("domain").definedBy("geun.hexdddstudy.domain..")
                .layer("application").definedBy("geun.hexdddstudy.application..")
                .layer("adapter").definedBy("geun.hexdddstudy.adapter..")
                .whereLayer("domain").mayOnlyBeAccessedByLayers("application", "adapter")
                .whereLayer("application").mayOnlyBeAccessedByLayers("adapter")
                .whereLayer("adapter").mayNotBeAccessedByAnyLayer()
                .check(classes);
    }
}
