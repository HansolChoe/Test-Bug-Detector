package com.github.spotbugs;

import edu.umd.cs.findbugs.BugCollection;
import edu.umd.cs.findbugs.test.SpotBugsRule;
import edu.umd.cs.findbugs.test.matcher.BugInstanceMatcher;
import edu.umd.cs.findbugs.test.matcher.BugInstanceMatcherBuilder;
import org.junit.Rule;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static edu.umd.cs.findbugs.test.CountMatcher.containsExactly;
import static org.hamcrest.MatcherAssert.assertThat;

public class MavenTestClassNameDetectorTest {
    @Rule
    public SpotBugsRule spotbugs = new SpotBugsRule();

    @Test
    public void testGoodCase1() {
        Path path = Paths.get("target/test-classes", "com.github.spotbugs".replace('.', '/'), "TestClassNameGoodCase.class");
        BugCollection bugCollection = spotbugs.performAnalysis(path);
        BugInstanceMatcher bugTypeMatcher = new BugInstanceMatcherBuilder()
                .bugType("TEST_CLASS_NAME_NOT_DEFAULT").build();
        assertThat(bugCollection, containsExactly(0, bugTypeMatcher));
    }

    @Test
    public void testBadCase1() {
        Path path = Paths.get("target/test-classes", "com.github.spotbugs".replace('.', '/'), "SomeTestClass.class");
        BugCollection bugCollection = spotbugs.performAnalysis(path);
        BugInstanceMatcher bugTypeMatcher = new BugInstanceMatcherBuilder()
                .bugType("TEST_CLASS_NAME_NOT_DEFAULT").build();
        assertThat(bugCollection, containsExactly(1, bugTypeMatcher));
    }

    @Test
    public void testBadCase2() {
        Path path = Paths.get("target/test-classes", "com.github.spotbugs".replace('.', '/'), "TeztClassNameBadCase.class");
        BugCollection bugCollection = spotbugs.performAnalysis(path);

        BugInstanceMatcher bugTypeMatcher = new BugInstanceMatcherBuilder()
                .bugType("TEST_CLASS_NAME_NOT_DEFAULT").build();
        assertThat(bugCollection, containsExactly(1, bugTypeMatcher));
    }
}