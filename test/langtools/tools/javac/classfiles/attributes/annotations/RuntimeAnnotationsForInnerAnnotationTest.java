/*
 * Copyright (c) 2015, 2016, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

/*
 * @test
 * @bug 8044411
 * @summary Tests the RuntimeVisibleAnnotations/RuntimeInvisibleAnnotations attribute.
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 * @library /tools/lib /tools/javac/lib ../lib
 * @build toolbox.ToolBox InMemoryFileManager TestResult TestBase
 * @build WorkAnnotations TestCase ClassType TestAnnotationInfo
 * @build RuntimeAnnotationsForInnerAnnotationTest AnnotationsTestBase RuntimeAnnotationsTestBase
 * @run main RuntimeAnnotationsForInnerAnnotationTest
 */

import java.util.ArrayList;
import java.util.List;

/**
 * The test checks that RuntimeVisibleAnnotationsAttribute and RuntimeInvisibleAnnotationsAttribute
 * are generated properly for inner classes annotations, for methods, for fields
 *
 * The test checks both single and repeatable annotations. In addition, all possible combinations
 * of retention policies are tested. The test generates source code, compiles it and checks the byte code.
 *
 * See README.txt for more information.
 */
public class RuntimeAnnotationsForInnerAnnotationTest extends RuntimeAnnotationsTestBase {
    @Override
    public List<TestCase> generateTestCases() {
        List<TestCase> testCases = new ArrayList<>();
        for (List<TestAnnotationInfos> groupedAnnotations : groupAnnotations(getAllCombinationsOfAnnotations())) {
            for (ClassType outerClassType : ClassType.values()) {
                TestCase test = new TestCase();
                TestCase.TestClassInfo clazz = test.addClassInfo(outerClassType, "Test");
                for (int i = 0; i < groupedAnnotations.size(); ++i) {
                    TestCase.TestClassInfo anno = clazz.addInnerClassInfo(ClassType.ANNOTATION, "InnerAnnotation" + i);
                    TestAnnotationInfos annotations = groupedAnnotations.get(i);
                    annotations.annotate(anno);

                    TestCase.TestMethodInfo annoMethod = anno.addMethodInfo("interMethod" + i + "()");
                    annotations.annotate(annoMethod);

                    TestCase.TestFieldInfo annoField = anno.addFieldInfo("annoField" + i);
                    annotations.annotate(annoField);
                }
                testCases.add(test);
            }
        }
        return testCases;
    }

    public static void main(String[] args) throws TestFailedException {
        new RuntimeAnnotationsForInnerAnnotationTest().test();
    }
}
