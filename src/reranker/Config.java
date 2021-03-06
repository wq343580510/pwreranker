package reranker;

import edu.stanford.nlp.international.Languages;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.util.PropertiesUtils;
import edu.stanford.nlp.util.ReflectionLoading;

import java.util.List;
import java.util.Properties;
import java.util.function.Function;

/**
 * Created by zhouh on 15-12-15.
 *
 */
public class Config {
    /**
     *   Out-of-vocabulary token string.
     */
    public static final String UNKNOWN = "-UNKNOWN-";

    /**
     *   Root token string.
     */
    public static final String ROOT = "-ROOT-";

    /**
     *   Non-existent token string.
     */
    public static final String NULL = "-NULL-";

    /**
     *   Represent a non-existent token.
     */
    public static final int NONEXIST = -1;

    /**
     *   For printing messages.
     */
    public static final String SEPARATOR = "###################";

    /**
     * The language being parsed.
     */
    public Languages.Language language = Languages.Language.English;

    /**
     * Number of threads to use during training. Also indirectly controls
     * how mini-batches are partitioned (more threads => more partitions
     * => smaller partitions).
     */
    public int trainingThreads = 1;

    /**
     * Refuse to train on words which have a corpus frequency less than
     * this number.
     */
    public int wordCutOff = 1;

    /**
     * Model weights will be initialized to random values within the
     * range {@code [-initRange, initRange]}.
     */
    public double initRange = 0.01;

    /**
     * Maximum number of iterations for training
     */
    public int maxIter = 50;

    /**
     * Size of mini-batch for training. A random subset of training
     * examples of this size will be used to train the classifier on each
     * iteration.
     */
    public int batchSize = 5;

    /*
     *   max size of the output n-best
     */
    public int nMaxN = 100;

    /*
     *  file index, for index the line of reranking n best list
     *  because reranking data is generated from different file
     */
    public int nFileIndex = 1;



    /**
     * An epsilon value added to the denominator of the AdaGrad
     * expression for numerical stability
     */
    public double adaEps = 1e-6;

    /**
     * Initial global learning rate for AdaGrad training
     */
    public double adaAlpha = 0.1;

    /**
     * Regularization parameter. All weight updates are scaled by this
     * single parameter.
     */
    public double regParameter = 1e-4;

    /**
     * Dropout probability. For each training example we randomly choose
     * some amount of units to disable in the neural network classifier.
     * This probability controls the proportion of units "dropped out."
     */
    public double dropProb = 0.5;

    /**
     * Size of the neural network hidden layer.
     */
    public int hiddenSize = 200;

    /**
     * hcdepth
     * the search depth in hc search
     */
    public int nHcDepth = 1;

    /**
     * Dimensionality of the word embeddings used
     */
    public int embeddingSize = 50;

    /**
     * Total number of tokens provided as input to the classifier. (Each
     * token is provided in word embedding form.)
     */
    // TODO: we can figure this out automatically based on features used.
    // Should remove this option once we make feature templates / dynamic features
    public static final int numTokens = 32;

    /**
     * Number of input tokens for which we should compute hidden-layer
     * unit activations.
     *
     * If zero, the parser will skip the pre-computation step.
     */
    public int numPreComputed = 100000;

    /**
     * During training, run a full UAS evaluation after every
     * {@code evalPerIter} iterations.
     */
    public int evalPerIter = 100;

    /**
     * During training, clear AdaGrad gradient histories after every
     * {@code clearGradientsPerIter} iterations. (If zero, never clear
     * gradients.)
     */
    public int clearGradientsPerIter = 0;

    /*
     *   the n-best test file num
     *   for 10-folds jacknifing
     */
    public int nNBestFileNum = 1;

    /*
     *   the max action margin for hc reranking
     */
    public double dMargin = 0.95;
    public double dAlpha = Double.NEGATIVE_INFINITY;
    public double dBetha = Double.NEGATIVE_INFINITY;

    /*
     *   the base of the exponential function
     *
     */
    public double dBase = 2;

    /*
     *   the max num of adopted revise action from one complete
     *   parsing state
     */
    public int nMaxReviseActNum = 10;

    /*
     *   the get n-best strategy:
     *   reviseOneAct;
     *   bestFirstRevise;
     *   beamNBest;
     */
    public boolean bReviseOneAct = false;
    public boolean bBestFirstRevise = false;
    public boolean bBeamNBest = false;
    /**
     * Save an intermediate model file whenever we see an improved UAS
     * evaluation. (The frequency of these evaluations is configurable as
     * well; see {@link #evalPerIter}.)
     */
    public boolean saveIntermediate = true;

    /**
     * Describes language-specific properties necessary for training and
     * testing. By default,
     * {@link edu.stanford.nlp.trees.PennTreebankLanguagePack} will be
     * used.
     */
    public TreebankLanguagePack tlp;

    public boolean bCheckUnknowRule  = false;

    // --- Runtime parsing options

    /**
     * If non-null, when parsing raw text assume sentences have already
     * been split and are separated by the given delimiter.
     *
     * If null, the parser splits sentences automatically.
     */
    public String sentenceDelimiter = null;

    public String sMERTOutputFile = null;

    /**
     * Defines a word-escaper to use when parsing raw sentences.
     *
     * As a command-line option, you should provide the fully qualified
     * class name of a valid escaper (that is, a class which implements
     * {@code Function<List<HasWord>, List<HasWord>>}).
     */
    public Function<List<HasWord>, List<HasWord>> escaper = null;

    /**
     * Path to a tagger file compatible with
     * {@link MaxentTagger}.
     */
    public String tagger = MaxentTagger.DEFAULT_JAR_PATH;
    public int onlieOnceReadNum = 5000;
    public int pairwiseRankNBest = 8;
    public boolean bRerankerAddBaseScore = false;
    public boolean bUseRankingRNN = false;
    public boolean bUsePWGEarlyUpdate = false;
    public boolean bUseSURNNScoreTest = false;
    public boolean bUseCharniarkScoreTest = false;

    public Config(Properties properties) {
        setProperties(properties);
    }

    private void setProperties(Properties props) {
        trainingThreads = PropertiesUtils.getInt(props, "trainingThreads", trainingThreads);
        wordCutOff = PropertiesUtils.getInt(props, "wordCutOff", wordCutOff);
        initRange = PropertiesUtils.getDouble(props, "initRange", initRange);
        maxIter = PropertiesUtils.getInt(props, "maxIter", maxIter);
        batchSize = PropertiesUtils.getInt(props, "batchSize", batchSize);
        adaEps = PropertiesUtils.getDouble(props, "adaEps", adaEps);
        adaAlpha = PropertiesUtils.getDouble(props, "adaAlpha", adaAlpha);
        regParameter = PropertiesUtils.getDouble(props, "regParameter", regParameter);
        dropProb = PropertiesUtils.getDouble(props, "dropProb", dropProb);
        hiddenSize = PropertiesUtils.getInt(props, "hiddenSize", hiddenSize);
        embeddingSize = PropertiesUtils.getInt(props, "embeddingSize", embeddingSize);
        numPreComputed = PropertiesUtils.getInt(props, "numPreComputed", numPreComputed);
        evalPerIter = PropertiesUtils.getInt(props, "evalPerIter", evalPerIter);
        clearGradientsPerIter = PropertiesUtils.getInt(props, "clearGradientsPerIter", clearGradientsPerIter);
        saveIntermediate = PropertiesUtils.getBool(props, "saveIntermediate", saveIntermediate);
        nHcDepth = PropertiesUtils.getInt(props, "nHcDepth", nHcDepth);
        nNBestFileNum = PropertiesUtils.getInt(props, "nNBestFileNum", nNBestFileNum);
        dMargin = PropertiesUtils.getDouble(props, "dMargin", dMargin);
        nMaxN = PropertiesUtils.getInt(props, "nMaxN", nMaxN);
        dBase = PropertiesUtils.getDouble(props, "dBase", dBase);
        dAlpha = PropertiesUtils.getDouble(props, "dAlpha", dAlpha);
        dBetha = PropertiesUtils.getDouble(props, "dBetha", dBetha);
        nFileIndex = PropertiesUtils.getInt(props, "nFileIndex", nFileIndex);
        nMaxReviseActNum = PropertiesUtils.getInt(props, "nMaxReviseActNum", nMaxReviseActNum);
        bBeamNBest = PropertiesUtils.getBool(props, "bBeamNBest", bBeamNBest);
        bBestFirstRevise = PropertiesUtils.getBool(props, "bBestFirstRevise", bBestFirstRevise);
        bReviseOneAct = PropertiesUtils.getBool(props, "bReviseOneAct", bReviseOneAct);
        onlieOnceReadNum = PropertiesUtils.getInt(props, "onlieOnceReadNum", onlieOnceReadNum);
        pairwiseRankNBest = PropertiesUtils.getInt(props, "pairwiseRankNBest", pairwiseRankNBest);
        bCheckUnknowRule = PropertiesUtils.getBool(props, "bCheckUnknowRule", bCheckUnknowRule);
        bRerankerAddBaseScore = PropertiesUtils.getBool(props, "bRerankerAddBaseScore", bRerankerAddBaseScore);
        bUseRankingRNN = PropertiesUtils.getBool(props, "bUseRankingRNN", bUseRankingRNN);
        bUsePWGEarlyUpdate = PropertiesUtils.getBool(props, "bUsePWGEarlyUpdate", bUsePWGEarlyUpdate);
        bUseSURNNScoreTest = PropertiesUtils.getBool(props, "bUseSURNNScoreTest", bUseSURNNScoreTest);
        bUseCharniarkScoreTest = PropertiesUtils.getBool(props, "bUseCharniarkScoreTest", bUseCharniarkScoreTest);

        // Runtime parsing options
        sentenceDelimiter = PropertiesUtils.getString(props, "sentenceDelimiter", sentenceDelimiter);
        tagger = PropertiesUtils.getString(props, "tagger.model", tagger);
        sMERTOutputFile = PropertiesUtils.getString(props, "sMERTOutputFile", sMERTOutputFile);

        String escaperClass = props.getProperty("escaper");
        escaper = escaperClass != null ? ReflectionLoading.loadByReflection(escaperClass) : null;

        // Language options
        language = props.containsKey("language")
                ? getLanguage(props.getProperty("language"))
                : language;
        tlp = Languages.getLanguageParams(language).treebankLanguagePack();
    }

    /**
     * Get the {@link Languages.Language}
     * object corresponding to the given language string.
     *
     * @return A {@link Languages.Language}
     *         or {@code null} if no instance matches the given string.
     */
    private Languages.Language getLanguage(String languageStr) {
        for (Languages.Language l : Languages.Language.values()) {
            if (l.name().equalsIgnoreCase(languageStr))
                return l;
        }

        return null;
    }

    public void printParameters() {
        System.err.printf("language = %s%n", language);
        System.err.printf("trainingThreads = %d%n", trainingThreads);
        System.err.printf("wordCutOff = %d%n", wordCutOff);
        System.err.printf("initRange = %.2g%n", initRange);
        System.err.printf("maxIter = %d%n", maxIter);
        System.err.printf("batchSize = %d%n", batchSize);
        System.err.printf("adaEps = %.2g%n", adaEps);
        System.err.printf("adaAlpha = %.2g%n", adaAlpha);
        System.err.printf("regParameter = %.2g%n", regParameter);
        System.err.printf("dropProb = %.2g%n", dropProb);
        System.err.printf("hiddenSize = %d%n", hiddenSize);
        System.err.printf("embeddingSize = %d%n", embeddingSize);
        System.err.printf("numPreComputed = %d%n", numPreComputed);
        System.err.printf("evalPerIter = %d%n", evalPerIter);
        System.err.printf("clearGradientsPerIter = %d%n", clearGradientsPerIter);
        System.err.printf("saveItermediate = %b%n", saveIntermediate);
    }
}
