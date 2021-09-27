package javamodularity.easytext.algorithm.kincaid;

import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import javamodularity.easytext.algorithm.api.Analyzer;
import javamodularity.easytext.algorithm.api.SyllableCounter;

public class KincaidAnalyzer implements Analyzer {

   private final SyllableCounter syllableCounter;

   public KincaidAnalyzer() {
      syllableCounter = ServiceLoader.load(SyllableCounter.class)
              .findFirst()
              .orElseThrow(() -> new IllegalStateException("SyllableCounter not found"));
   }

   public String getName() {
      return "Flesch-Kincaid";
   }

   public double analyze(List<List<String>> sentences) {
      float totalSentences = sentences.size();
      float totalWords = sentences.stream().mapToInt(List::size).sum();
      float totalSyllables = sentences.stream()
         .flatMapToInt(sentence ->
            sentence.stream().mapToInt(syllableCounter::countSyllables))
         .sum();
      return 206.835 - 1.015 * (totalWords / totalSentences) - 84.6 * (totalSyllables / totalWords);
   }

}
