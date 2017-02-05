"# MleapCustomizationPOC"

Example for writing a custom FeaturesExtractor (A Transformer) -  which uses a custom data type - in Sprak,
 serializing it as a stage in mleap pipeline and reading it from the serialized file.

Feature is a custom data type. It is a simple case class, containing all the data related to a single feature that the
 model can extract: id, description and value.
 In this example, the description is the ngram (String) that the model can identify in the input text.

In order to serialize the Feature objects, we have to implement FeatureCustomType - a mleap CustomType that supports serializing a type to JSON.
 The custom type should be registered to mleap context in training and runtime
 * ScalaPipelineMleap: SparkBundleContext.defaultContext.bundleRegistry.register(new FeatureCustomType)
 * LoadMleapModel: implicit val ctx = MleapContext.defaultContext.withCustomType(new FeatureCustomType)

NgramModel is a dummy implementation of a Model (the logic which is used to extract features). It is initialized with a
  predefined list of Feature objects.

NgramSparkTrainer is an org.apache.spark.ml.Estimator that trains an NgramModel using a labeled examples set. It wraps the
model with an NgramSparkModel (implementing org.apache.spark.ml.Model) which is used in the fitting of Spark Pipeline (See ScalaPipelineMleap).

FeaturesExtractorSparkSupport which is a base trait for all custom feature extractors, providing access methods to inputCol and outputCol (note that due to Sparks' extra privacy the HasInputCols and HasOutputCol traits were copied to
  my project with a less strict boundaries).

NgramSparkSupport is a base trait for all Ngram training APIs (NgramSparkTrainer and NgramSparkModel), providing some common methods.

com.tms.ml.feature.ngram.training.NgramMleapOp (note that there are two 'NgramMleapOp' classes - one in 'training' package and one in 'runtime')
  is used by mleap to serialize the NgramSparkModel. internally, is uses NgramModelMleapOp to serialize the model itself. The model is serialized to a JSON file
  listing all the features.

The NgramMleapOp should be registered to mleap context in train time:
 * ScalaPipelineMleap: SparkBundleContext.defaultContext.bundleRegistry.register(new NgramMleapOp)

When loading stored mleap pipeline, com.tms.ml.feature.ngram.runtime.NgramMleapOp loads the model (using the same NgramModelMleapOp used in training),
  and wraps it with an com.tms.ml.feature.ngram.runtime.NgramMleapFeaturesExtractor which extends mleap FeatureTransformer.