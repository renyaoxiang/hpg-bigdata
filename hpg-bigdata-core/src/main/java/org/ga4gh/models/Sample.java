/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package org.ga4gh.models;  
@SuppressWarnings("all")
/** A biological sample used in an experiment. (e.g. whole blood from
an affected individual) */
@org.apache.avro.specific.AvroGenerated
public class Sample extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Sample\",\"namespace\":\"org.ga4gh.models\",\"doc\":\"A biological sample used in an experiment. (e.g. whole blood from\\nan affected individual)\",\"fields\":[{\"name\":\"id\",\"type\":\"string\",\"doc\":\"The sample UUID. This is globally unique.\"},{\"name\":\"individualId\",\"type\":[\"null\",\"string\"],\"doc\":\"The ID of the individual this sample belongs to.\",\"default\":null},{\"name\":\"accessions\",\"type\":{\"type\":\"array\",\"items\":\"string\"},\"doc\":\"Public identifiers for this sample.\",\"default\":[]},{\"name\":\"name\",\"type\":[\"null\",\"string\"],\"doc\":\"The name of the sample.\",\"default\":null},{\"name\":\"description\",\"type\":[\"null\",\"string\"],\"doc\":\"A description of the sample.\",\"default\":null},{\"name\":\"created\",\"type\":[\"null\",\"long\"],\"doc\":\"The time at which this sample was created in milliseconds from the epoch.\",\"default\":null},{\"name\":\"updated\",\"type\":[\"null\",\"long\"],\"doc\":\"The time at which this sample was last updated in milliseconds\\n  from the epoch.\",\"default\":null},{\"name\":\"samplingDate\",\"type\":[\"null\",\"long\"],\"doc\":\"The time at which this sample was taken from the individual, in milliseconds\\n  from the epoch.\",\"default\":null},{\"name\":\"age\",\"type\":[\"null\",\"long\"],\"doc\":\"The age of this sample in months. TODO: is months the right format?\\n  This field may be approximate.\",\"default\":null},{\"name\":\"cellType\",\"type\":[\"null\",{\"type\":\"record\",\"name\":\"OntologyTerm\",\"doc\":\"An ontology term describing an attribute. (e.g. the phenotype attribute\\n'polydactyly' from HPO)\",\"fields\":[{\"name\":\"ontologySource\",\"type\":\"string\",\"doc\":\"The source of the onotology term.\\n  (e.g. `Ontology for Biomedical Investigation`)\"},{\"name\":\"id\",\"type\":\"string\",\"doc\":\"The ID defined by the external onotology source.\\n  (e.g. `http://purl.obolibrary.org/obo/OBI_0001271`)\"},{\"name\":\"name\",\"type\":[\"null\",\"string\"],\"doc\":\"The name of the onotology term. (e.g. `RNA-seq assay`)\",\"default\":null}]}],\"doc\":\"The cell type of this sample.\\n  Using the [Cell Ontology](http://cellontology.org/) (CL) is recommended. See\",\"default\":null},{\"name\":\"cellLine\",\"type\":[\"null\",\"OntologyTerm\"],\"doc\":\"The cell line of this sample. \\n  Using the [Cell Line Ontology](https://code.google.com/p/clo-ontology/) is a possibility.\\n  TODO: discuss further. Other possibilities: Cellosaurus (nextprot), BRENDA/BTO, EFO (EBI)\",\"default\":null},{\"name\":\"geocode\",\"type\":[\"null\",\"string\"],\"doc\":\"Geographic coordinates from which the individual was obtained.\\n  TODO: Figure out the right type for this field.\",\"default\":null},{\"name\":\"sampleType\",\"type\":[\"null\",\"string\"],\"doc\":\"A descriptor of the sample type. (e.g. `frozen`)\",\"default\":null},{\"name\":\"organismPart\",\"type\":[\"null\",\"OntologyTerm\"],\"doc\":\"The anatomical part of the individual from which this sample derives.\\n  Using [Uberon](http://uberon.org) is recommended.\",\"default\":null},{\"name\":\"info\",\"type\":{\"type\":\"map\",\"values\":{\"type\":\"array\",\"items\":\"string\"}},\"doc\":\"A map of additional sample information.\",\"default\":{}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  /** The sample UUID. This is globally unique. */
  @Deprecated public java.lang.CharSequence id;
  /** The ID of the individual this sample belongs to. */
  @Deprecated public java.lang.CharSequence individualId;
  /** Public identifiers for this sample. */
  @Deprecated public java.util.List<java.lang.CharSequence> accessions;
  /** The name of the sample. */
  @Deprecated public java.lang.CharSequence name;
  /** A description of the sample. */
  @Deprecated public java.lang.CharSequence description;
  /** The time at which this sample was created in milliseconds from the epoch. */
  @Deprecated public java.lang.Long created;
  /** The time at which this sample was last updated in milliseconds
  from the epoch. */
  @Deprecated public java.lang.Long updated;
  /** The time at which this sample was taken from the individual, in milliseconds
  from the epoch. */
  @Deprecated public java.lang.Long samplingDate;
  /** The age of this sample in months. TODO: is months the right format?
  This field may be approximate. */
  @Deprecated public java.lang.Long age;
  /** The cell type of this sample.
  Using the [Cell Ontology](http://cellontology.org/) (CL) is recommended. See */
  @Deprecated public org.ga4gh.models.OntologyTerm cellType;
  /** The cell line of this sample. 
  Using the [Cell Line Ontology](https://code.google.com/p/clo-ontology/) is a possibility.
  TODO: discuss further. Other possibilities: Cellosaurus (nextprot), BRENDA/BTO, EFO (EBI) */
  @Deprecated public org.ga4gh.models.OntologyTerm cellLine;
  /** Geographic coordinates from which the individual was obtained.
  TODO: Figure out the right type for this field. */
  @Deprecated public java.lang.CharSequence geocode;
  /** A descriptor of the sample type. (e.g. `frozen`) */
  @Deprecated public java.lang.CharSequence sampleType;
  /** The anatomical part of the individual from which this sample derives.
  Using [Uberon](http://uberon.org) is recommended. */
  @Deprecated public org.ga4gh.models.OntologyTerm organismPart;
  /** A map of additional sample information. */
  @Deprecated public java.util.Map<java.lang.CharSequence,java.util.List<java.lang.CharSequence>> info;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>. 
   */
  public Sample() {}

  /**
   * All-args constructor.
   */
  public Sample(java.lang.CharSequence id, java.lang.CharSequence individualId, java.util.List<java.lang.CharSequence> accessions, java.lang.CharSequence name, java.lang.CharSequence description, java.lang.Long created, java.lang.Long updated, java.lang.Long samplingDate, java.lang.Long age, org.ga4gh.models.OntologyTerm cellType, org.ga4gh.models.OntologyTerm cellLine, java.lang.CharSequence geocode, java.lang.CharSequence sampleType, org.ga4gh.models.OntologyTerm organismPart, java.util.Map<java.lang.CharSequence,java.util.List<java.lang.CharSequence>> info) {
    this.id = id;
    this.individualId = individualId;
    this.accessions = accessions;
    this.name = name;
    this.description = description;
    this.created = created;
    this.updated = updated;
    this.samplingDate = samplingDate;
    this.age = age;
    this.cellType = cellType;
    this.cellLine = cellLine;
    this.geocode = geocode;
    this.sampleType = sampleType;
    this.organismPart = organismPart;
    this.info = info;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return id;
    case 1: return individualId;
    case 2: return accessions;
    case 3: return name;
    case 4: return description;
    case 5: return created;
    case 6: return updated;
    case 7: return samplingDate;
    case 8: return age;
    case 9: return cellType;
    case 10: return cellLine;
    case 11: return geocode;
    case 12: return sampleType;
    case 13: return organismPart;
    case 14: return info;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: id = (java.lang.CharSequence)value$; break;
    case 1: individualId = (java.lang.CharSequence)value$; break;
    case 2: accessions = (java.util.List<java.lang.CharSequence>)value$; break;
    case 3: name = (java.lang.CharSequence)value$; break;
    case 4: description = (java.lang.CharSequence)value$; break;
    case 5: created = (java.lang.Long)value$; break;
    case 6: updated = (java.lang.Long)value$; break;
    case 7: samplingDate = (java.lang.Long)value$; break;
    case 8: age = (java.lang.Long)value$; break;
    case 9: cellType = (org.ga4gh.models.OntologyTerm)value$; break;
    case 10: cellLine = (org.ga4gh.models.OntologyTerm)value$; break;
    case 11: geocode = (java.lang.CharSequence)value$; break;
    case 12: sampleType = (java.lang.CharSequence)value$; break;
    case 13: organismPart = (org.ga4gh.models.OntologyTerm)value$; break;
    case 14: info = (java.util.Map<java.lang.CharSequence,java.util.List<java.lang.CharSequence>>)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'id' field.
   * The sample UUID. This is globally unique.   */
  public java.lang.CharSequence getId() {
    return id;
  }

  /**
   * Sets the value of the 'id' field.
   * The sample UUID. This is globally unique.   * @param value the value to set.
   */
  public void setId(java.lang.CharSequence value) {
    this.id = value;
  }

  /**
   * Gets the value of the 'individualId' field.
   * The ID of the individual this sample belongs to.   */
  public java.lang.CharSequence getIndividualId() {
    return individualId;
  }

  /**
   * Sets the value of the 'individualId' field.
   * The ID of the individual this sample belongs to.   * @param value the value to set.
   */
  public void setIndividualId(java.lang.CharSequence value) {
    this.individualId = value;
  }

  /**
   * Gets the value of the 'accessions' field.
   * Public identifiers for this sample.   */
  public java.util.List<java.lang.CharSequence> getAccessions() {
    return accessions;
  }

  /**
   * Sets the value of the 'accessions' field.
   * Public identifiers for this sample.   * @param value the value to set.
   */
  public void setAccessions(java.util.List<java.lang.CharSequence> value) {
    this.accessions = value;
  }

  /**
   * Gets the value of the 'name' field.
   * The name of the sample.   */
  public java.lang.CharSequence getName() {
    return name;
  }

  /**
   * Sets the value of the 'name' field.
   * The name of the sample.   * @param value the value to set.
   */
  public void setName(java.lang.CharSequence value) {
    this.name = value;
  }

  /**
   * Gets the value of the 'description' field.
   * A description of the sample.   */
  public java.lang.CharSequence getDescription() {
    return description;
  }

  /**
   * Sets the value of the 'description' field.
   * A description of the sample.   * @param value the value to set.
   */
  public void setDescription(java.lang.CharSequence value) {
    this.description = value;
  }

  /**
   * Gets the value of the 'created' field.
   * The time at which this sample was created in milliseconds from the epoch.   */
  public java.lang.Long getCreated() {
    return created;
  }

  /**
   * Sets the value of the 'created' field.
   * The time at which this sample was created in milliseconds from the epoch.   * @param value the value to set.
   */
  public void setCreated(java.lang.Long value) {
    this.created = value;
  }

  /**
   * Gets the value of the 'updated' field.
   * The time at which this sample was last updated in milliseconds
  from the epoch.   */
  public java.lang.Long getUpdated() {
    return updated;
  }

  /**
   * Sets the value of the 'updated' field.
   * The time at which this sample was last updated in milliseconds
  from the epoch.   * @param value the value to set.
   */
  public void setUpdated(java.lang.Long value) {
    this.updated = value;
  }

  /**
   * Gets the value of the 'samplingDate' field.
   * The time at which this sample was taken from the individual, in milliseconds
  from the epoch.   */
  public java.lang.Long getSamplingDate() {
    return samplingDate;
  }

  /**
   * Sets the value of the 'samplingDate' field.
   * The time at which this sample was taken from the individual, in milliseconds
  from the epoch.   * @param value the value to set.
   */
  public void setSamplingDate(java.lang.Long value) {
    this.samplingDate = value;
  }

  /**
   * Gets the value of the 'age' field.
   * The age of this sample in months. TODO: is months the right format?
  This field may be approximate.   */
  public java.lang.Long getAge() {
    return age;
  }

  /**
   * Sets the value of the 'age' field.
   * The age of this sample in months. TODO: is months the right format?
  This field may be approximate.   * @param value the value to set.
   */
  public void setAge(java.lang.Long value) {
    this.age = value;
  }

  /**
   * Gets the value of the 'cellType' field.
   * The cell type of this sample.
  Using the [Cell Ontology](http://cellontology.org/) (CL) is recommended. See   */
  public org.ga4gh.models.OntologyTerm getCellType() {
    return cellType;
  }

  /**
   * Sets the value of the 'cellType' field.
   * The cell type of this sample.
  Using the [Cell Ontology](http://cellontology.org/) (CL) is recommended. See   * @param value the value to set.
   */
  public void setCellType(org.ga4gh.models.OntologyTerm value) {
    this.cellType = value;
  }

  /**
   * Gets the value of the 'cellLine' field.
   * The cell line of this sample. 
  Using the [Cell Line Ontology](https://code.google.com/p/clo-ontology/) is a possibility.
  TODO: discuss further. Other possibilities: Cellosaurus (nextprot), BRENDA/BTO, EFO (EBI)   */
  public org.ga4gh.models.OntologyTerm getCellLine() {
    return cellLine;
  }

  /**
   * Sets the value of the 'cellLine' field.
   * The cell line of this sample. 
  Using the [Cell Line Ontology](https://code.google.com/p/clo-ontology/) is a possibility.
  TODO: discuss further. Other possibilities: Cellosaurus (nextprot), BRENDA/BTO, EFO (EBI)   * @param value the value to set.
   */
  public void setCellLine(org.ga4gh.models.OntologyTerm value) {
    this.cellLine = value;
  }

  /**
   * Gets the value of the 'geocode' field.
   * Geographic coordinates from which the individual was obtained.
  TODO: Figure out the right type for this field.   */
  public java.lang.CharSequence getGeocode() {
    return geocode;
  }

  /**
   * Sets the value of the 'geocode' field.
   * Geographic coordinates from which the individual was obtained.
  TODO: Figure out the right type for this field.   * @param value the value to set.
   */
  public void setGeocode(java.lang.CharSequence value) {
    this.geocode = value;
  }

  /**
   * Gets the value of the 'sampleType' field.
   * A descriptor of the sample type. (e.g. `frozen`)   */
  public java.lang.CharSequence getSampleType() {
    return sampleType;
  }

  /**
   * Sets the value of the 'sampleType' field.
   * A descriptor of the sample type. (e.g. `frozen`)   * @param value the value to set.
   */
  public void setSampleType(java.lang.CharSequence value) {
    this.sampleType = value;
  }

  /**
   * Gets the value of the 'organismPart' field.
   * The anatomical part of the individual from which this sample derives.
  Using [Uberon](http://uberon.org) is recommended.   */
  public org.ga4gh.models.OntologyTerm getOrganismPart() {
    return organismPart;
  }

  /**
   * Sets the value of the 'organismPart' field.
   * The anatomical part of the individual from which this sample derives.
  Using [Uberon](http://uberon.org) is recommended.   * @param value the value to set.
   */
  public void setOrganismPart(org.ga4gh.models.OntologyTerm value) {
    this.organismPart = value;
  }

  /**
   * Gets the value of the 'info' field.
   * A map of additional sample information.   */
  public java.util.Map<java.lang.CharSequence,java.util.List<java.lang.CharSequence>> getInfo() {
    return info;
  }

  /**
   * Sets the value of the 'info' field.
   * A map of additional sample information.   * @param value the value to set.
   */
  public void setInfo(java.util.Map<java.lang.CharSequence,java.util.List<java.lang.CharSequence>> value) {
    this.info = value;
  }

  /** Creates a new Sample RecordBuilder */
  public static org.ga4gh.models.Sample.Builder newBuilder() {
    return new org.ga4gh.models.Sample.Builder();
  }
  
  /** Creates a new Sample RecordBuilder by copying an existing Builder */
  public static org.ga4gh.models.Sample.Builder newBuilder(org.ga4gh.models.Sample.Builder other) {
    return new org.ga4gh.models.Sample.Builder(other);
  }
  
  /** Creates a new Sample RecordBuilder by copying an existing Sample instance */
  public static org.ga4gh.models.Sample.Builder newBuilder(org.ga4gh.models.Sample other) {
    return new org.ga4gh.models.Sample.Builder(other);
  }
  
  /**
   * RecordBuilder for Sample instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Sample>
    implements org.apache.avro.data.RecordBuilder<Sample> {

    private java.lang.CharSequence id;
    private java.lang.CharSequence individualId;
    private java.util.List<java.lang.CharSequence> accessions;
    private java.lang.CharSequence name;
    private java.lang.CharSequence description;
    private java.lang.Long created;
    private java.lang.Long updated;
    private java.lang.Long samplingDate;
    private java.lang.Long age;
    private org.ga4gh.models.OntologyTerm cellType;
    private org.ga4gh.models.OntologyTerm cellLine;
    private java.lang.CharSequence geocode;
    private java.lang.CharSequence sampleType;
    private org.ga4gh.models.OntologyTerm organismPart;
    private java.util.Map<java.lang.CharSequence,java.util.List<java.lang.CharSequence>> info;

    /** Creates a new Builder */
    private Builder() {
      super(org.ga4gh.models.Sample.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(org.ga4gh.models.Sample.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.individualId)) {
        this.individualId = data().deepCopy(fields()[1].schema(), other.individualId);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.accessions)) {
        this.accessions = data().deepCopy(fields()[2].schema(), other.accessions);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.name)) {
        this.name = data().deepCopy(fields()[3].schema(), other.name);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.description)) {
        this.description = data().deepCopy(fields()[4].schema(), other.description);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.created)) {
        this.created = data().deepCopy(fields()[5].schema(), other.created);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.updated)) {
        this.updated = data().deepCopy(fields()[6].schema(), other.updated);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.samplingDate)) {
        this.samplingDate = data().deepCopy(fields()[7].schema(), other.samplingDate);
        fieldSetFlags()[7] = true;
      }
      if (isValidValue(fields()[8], other.age)) {
        this.age = data().deepCopy(fields()[8].schema(), other.age);
        fieldSetFlags()[8] = true;
      }
      if (isValidValue(fields()[9], other.cellType)) {
        this.cellType = data().deepCopy(fields()[9].schema(), other.cellType);
        fieldSetFlags()[9] = true;
      }
      if (isValidValue(fields()[10], other.cellLine)) {
        this.cellLine = data().deepCopy(fields()[10].schema(), other.cellLine);
        fieldSetFlags()[10] = true;
      }
      if (isValidValue(fields()[11], other.geocode)) {
        this.geocode = data().deepCopy(fields()[11].schema(), other.geocode);
        fieldSetFlags()[11] = true;
      }
      if (isValidValue(fields()[12], other.sampleType)) {
        this.sampleType = data().deepCopy(fields()[12].schema(), other.sampleType);
        fieldSetFlags()[12] = true;
      }
      if (isValidValue(fields()[13], other.organismPart)) {
        this.organismPart = data().deepCopy(fields()[13].schema(), other.organismPart);
        fieldSetFlags()[13] = true;
      }
      if (isValidValue(fields()[14], other.info)) {
        this.info = data().deepCopy(fields()[14].schema(), other.info);
        fieldSetFlags()[14] = true;
      }
    }
    
    /** Creates a Builder by copying an existing Sample instance */
    private Builder(org.ga4gh.models.Sample other) {
            super(org.ga4gh.models.Sample.SCHEMA$);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.individualId)) {
        this.individualId = data().deepCopy(fields()[1].schema(), other.individualId);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.accessions)) {
        this.accessions = data().deepCopy(fields()[2].schema(), other.accessions);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.name)) {
        this.name = data().deepCopy(fields()[3].schema(), other.name);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.description)) {
        this.description = data().deepCopy(fields()[4].schema(), other.description);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.created)) {
        this.created = data().deepCopy(fields()[5].schema(), other.created);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.updated)) {
        this.updated = data().deepCopy(fields()[6].schema(), other.updated);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.samplingDate)) {
        this.samplingDate = data().deepCopy(fields()[7].schema(), other.samplingDate);
        fieldSetFlags()[7] = true;
      }
      if (isValidValue(fields()[8], other.age)) {
        this.age = data().deepCopy(fields()[8].schema(), other.age);
        fieldSetFlags()[8] = true;
      }
      if (isValidValue(fields()[9], other.cellType)) {
        this.cellType = data().deepCopy(fields()[9].schema(), other.cellType);
        fieldSetFlags()[9] = true;
      }
      if (isValidValue(fields()[10], other.cellLine)) {
        this.cellLine = data().deepCopy(fields()[10].schema(), other.cellLine);
        fieldSetFlags()[10] = true;
      }
      if (isValidValue(fields()[11], other.geocode)) {
        this.geocode = data().deepCopy(fields()[11].schema(), other.geocode);
        fieldSetFlags()[11] = true;
      }
      if (isValidValue(fields()[12], other.sampleType)) {
        this.sampleType = data().deepCopy(fields()[12].schema(), other.sampleType);
        fieldSetFlags()[12] = true;
      }
      if (isValidValue(fields()[13], other.organismPart)) {
        this.organismPart = data().deepCopy(fields()[13].schema(), other.organismPart);
        fieldSetFlags()[13] = true;
      }
      if (isValidValue(fields()[14], other.info)) {
        this.info = data().deepCopy(fields()[14].schema(), other.info);
        fieldSetFlags()[14] = true;
      }
    }

    /** Gets the value of the 'id' field */
    public java.lang.CharSequence getId() {
      return id;
    }
    
    /** Sets the value of the 'id' field */
    public org.ga4gh.models.Sample.Builder setId(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.id = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'id' field has been set */
    public boolean hasId() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'id' field */
    public org.ga4gh.models.Sample.Builder clearId() {
      id = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'individualId' field */
    public java.lang.CharSequence getIndividualId() {
      return individualId;
    }
    
    /** Sets the value of the 'individualId' field */
    public org.ga4gh.models.Sample.Builder setIndividualId(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.individualId = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'individualId' field has been set */
    public boolean hasIndividualId() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'individualId' field */
    public org.ga4gh.models.Sample.Builder clearIndividualId() {
      individualId = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'accessions' field */
    public java.util.List<java.lang.CharSequence> getAccessions() {
      return accessions;
    }
    
    /** Sets the value of the 'accessions' field */
    public org.ga4gh.models.Sample.Builder setAccessions(java.util.List<java.lang.CharSequence> value) {
      validate(fields()[2], value);
      this.accessions = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'accessions' field has been set */
    public boolean hasAccessions() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'accessions' field */
    public org.ga4gh.models.Sample.Builder clearAccessions() {
      accessions = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /** Gets the value of the 'name' field */
    public java.lang.CharSequence getName() {
      return name;
    }
    
    /** Sets the value of the 'name' field */
    public org.ga4gh.models.Sample.Builder setName(java.lang.CharSequence value) {
      validate(fields()[3], value);
      this.name = value;
      fieldSetFlags()[3] = true;
      return this; 
    }
    
    /** Checks whether the 'name' field has been set */
    public boolean hasName() {
      return fieldSetFlags()[3];
    }
    
    /** Clears the value of the 'name' field */
    public org.ga4gh.models.Sample.Builder clearName() {
      name = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /** Gets the value of the 'description' field */
    public java.lang.CharSequence getDescription() {
      return description;
    }
    
    /** Sets the value of the 'description' field */
    public org.ga4gh.models.Sample.Builder setDescription(java.lang.CharSequence value) {
      validate(fields()[4], value);
      this.description = value;
      fieldSetFlags()[4] = true;
      return this; 
    }
    
    /** Checks whether the 'description' field has been set */
    public boolean hasDescription() {
      return fieldSetFlags()[4];
    }
    
    /** Clears the value of the 'description' field */
    public org.ga4gh.models.Sample.Builder clearDescription() {
      description = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /** Gets the value of the 'created' field */
    public java.lang.Long getCreated() {
      return created;
    }
    
    /** Sets the value of the 'created' field */
    public org.ga4gh.models.Sample.Builder setCreated(java.lang.Long value) {
      validate(fields()[5], value);
      this.created = value;
      fieldSetFlags()[5] = true;
      return this; 
    }
    
    /** Checks whether the 'created' field has been set */
    public boolean hasCreated() {
      return fieldSetFlags()[5];
    }
    
    /** Clears the value of the 'created' field */
    public org.ga4gh.models.Sample.Builder clearCreated() {
      created = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    /** Gets the value of the 'updated' field */
    public java.lang.Long getUpdated() {
      return updated;
    }
    
    /** Sets the value of the 'updated' field */
    public org.ga4gh.models.Sample.Builder setUpdated(java.lang.Long value) {
      validate(fields()[6], value);
      this.updated = value;
      fieldSetFlags()[6] = true;
      return this; 
    }
    
    /** Checks whether the 'updated' field has been set */
    public boolean hasUpdated() {
      return fieldSetFlags()[6];
    }
    
    /** Clears the value of the 'updated' field */
    public org.ga4gh.models.Sample.Builder clearUpdated() {
      updated = null;
      fieldSetFlags()[6] = false;
      return this;
    }

    /** Gets the value of the 'samplingDate' field */
    public java.lang.Long getSamplingDate() {
      return samplingDate;
    }
    
    /** Sets the value of the 'samplingDate' field */
    public org.ga4gh.models.Sample.Builder setSamplingDate(java.lang.Long value) {
      validate(fields()[7], value);
      this.samplingDate = value;
      fieldSetFlags()[7] = true;
      return this; 
    }
    
    /** Checks whether the 'samplingDate' field has been set */
    public boolean hasSamplingDate() {
      return fieldSetFlags()[7];
    }
    
    /** Clears the value of the 'samplingDate' field */
    public org.ga4gh.models.Sample.Builder clearSamplingDate() {
      samplingDate = null;
      fieldSetFlags()[7] = false;
      return this;
    }

    /** Gets the value of the 'age' field */
    public java.lang.Long getAge() {
      return age;
    }
    
    /** Sets the value of the 'age' field */
    public org.ga4gh.models.Sample.Builder setAge(java.lang.Long value) {
      validate(fields()[8], value);
      this.age = value;
      fieldSetFlags()[8] = true;
      return this; 
    }
    
    /** Checks whether the 'age' field has been set */
    public boolean hasAge() {
      return fieldSetFlags()[8];
    }
    
    /** Clears the value of the 'age' field */
    public org.ga4gh.models.Sample.Builder clearAge() {
      age = null;
      fieldSetFlags()[8] = false;
      return this;
    }

    /** Gets the value of the 'cellType' field */
    public org.ga4gh.models.OntologyTerm getCellType() {
      return cellType;
    }
    
    /** Sets the value of the 'cellType' field */
    public org.ga4gh.models.Sample.Builder setCellType(org.ga4gh.models.OntologyTerm value) {
      validate(fields()[9], value);
      this.cellType = value;
      fieldSetFlags()[9] = true;
      return this; 
    }
    
    /** Checks whether the 'cellType' field has been set */
    public boolean hasCellType() {
      return fieldSetFlags()[9];
    }
    
    /** Clears the value of the 'cellType' field */
    public org.ga4gh.models.Sample.Builder clearCellType() {
      cellType = null;
      fieldSetFlags()[9] = false;
      return this;
    }

    /** Gets the value of the 'cellLine' field */
    public org.ga4gh.models.OntologyTerm getCellLine() {
      return cellLine;
    }
    
    /** Sets the value of the 'cellLine' field */
    public org.ga4gh.models.Sample.Builder setCellLine(org.ga4gh.models.OntologyTerm value) {
      validate(fields()[10], value);
      this.cellLine = value;
      fieldSetFlags()[10] = true;
      return this; 
    }
    
    /** Checks whether the 'cellLine' field has been set */
    public boolean hasCellLine() {
      return fieldSetFlags()[10];
    }
    
    /** Clears the value of the 'cellLine' field */
    public org.ga4gh.models.Sample.Builder clearCellLine() {
      cellLine = null;
      fieldSetFlags()[10] = false;
      return this;
    }

    /** Gets the value of the 'geocode' field */
    public java.lang.CharSequence getGeocode() {
      return geocode;
    }
    
    /** Sets the value of the 'geocode' field */
    public org.ga4gh.models.Sample.Builder setGeocode(java.lang.CharSequence value) {
      validate(fields()[11], value);
      this.geocode = value;
      fieldSetFlags()[11] = true;
      return this; 
    }
    
    /** Checks whether the 'geocode' field has been set */
    public boolean hasGeocode() {
      return fieldSetFlags()[11];
    }
    
    /** Clears the value of the 'geocode' field */
    public org.ga4gh.models.Sample.Builder clearGeocode() {
      geocode = null;
      fieldSetFlags()[11] = false;
      return this;
    }

    /** Gets the value of the 'sampleType' field */
    public java.lang.CharSequence getSampleType() {
      return sampleType;
    }
    
    /** Sets the value of the 'sampleType' field */
    public org.ga4gh.models.Sample.Builder setSampleType(java.lang.CharSequence value) {
      validate(fields()[12], value);
      this.sampleType = value;
      fieldSetFlags()[12] = true;
      return this; 
    }
    
    /** Checks whether the 'sampleType' field has been set */
    public boolean hasSampleType() {
      return fieldSetFlags()[12];
    }
    
    /** Clears the value of the 'sampleType' field */
    public org.ga4gh.models.Sample.Builder clearSampleType() {
      sampleType = null;
      fieldSetFlags()[12] = false;
      return this;
    }

    /** Gets the value of the 'organismPart' field */
    public org.ga4gh.models.OntologyTerm getOrganismPart() {
      return organismPart;
    }
    
    /** Sets the value of the 'organismPart' field */
    public org.ga4gh.models.Sample.Builder setOrganismPart(org.ga4gh.models.OntologyTerm value) {
      validate(fields()[13], value);
      this.organismPart = value;
      fieldSetFlags()[13] = true;
      return this; 
    }
    
    /** Checks whether the 'organismPart' field has been set */
    public boolean hasOrganismPart() {
      return fieldSetFlags()[13];
    }
    
    /** Clears the value of the 'organismPart' field */
    public org.ga4gh.models.Sample.Builder clearOrganismPart() {
      organismPart = null;
      fieldSetFlags()[13] = false;
      return this;
    }

    /** Gets the value of the 'info' field */
    public java.util.Map<java.lang.CharSequence,java.util.List<java.lang.CharSequence>> getInfo() {
      return info;
    }
    
    /** Sets the value of the 'info' field */
    public org.ga4gh.models.Sample.Builder setInfo(java.util.Map<java.lang.CharSequence,java.util.List<java.lang.CharSequence>> value) {
      validate(fields()[14], value);
      this.info = value;
      fieldSetFlags()[14] = true;
      return this; 
    }
    
    /** Checks whether the 'info' field has been set */
    public boolean hasInfo() {
      return fieldSetFlags()[14];
    }
    
    /** Clears the value of the 'info' field */
    public org.ga4gh.models.Sample.Builder clearInfo() {
      info = null;
      fieldSetFlags()[14] = false;
      return this;
    }

    @Override
    public Sample build() {
      try {
        Sample record = new Sample();
        record.id = fieldSetFlags()[0] ? this.id : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.individualId = fieldSetFlags()[1] ? this.individualId : (java.lang.CharSequence) defaultValue(fields()[1]);
        record.accessions = fieldSetFlags()[2] ? this.accessions : (java.util.List<java.lang.CharSequence>) defaultValue(fields()[2]);
        record.name = fieldSetFlags()[3] ? this.name : (java.lang.CharSequence) defaultValue(fields()[3]);
        record.description = fieldSetFlags()[4] ? this.description : (java.lang.CharSequence) defaultValue(fields()[4]);
        record.created = fieldSetFlags()[5] ? this.created : (java.lang.Long) defaultValue(fields()[5]);
        record.updated = fieldSetFlags()[6] ? this.updated : (java.lang.Long) defaultValue(fields()[6]);
        record.samplingDate = fieldSetFlags()[7] ? this.samplingDate : (java.lang.Long) defaultValue(fields()[7]);
        record.age = fieldSetFlags()[8] ? this.age : (java.lang.Long) defaultValue(fields()[8]);
        record.cellType = fieldSetFlags()[9] ? this.cellType : (org.ga4gh.models.OntologyTerm) defaultValue(fields()[9]);
        record.cellLine = fieldSetFlags()[10] ? this.cellLine : (org.ga4gh.models.OntologyTerm) defaultValue(fields()[10]);
        record.geocode = fieldSetFlags()[11] ? this.geocode : (java.lang.CharSequence) defaultValue(fields()[11]);
        record.sampleType = fieldSetFlags()[12] ? this.sampleType : (java.lang.CharSequence) defaultValue(fields()[12]);
        record.organismPart = fieldSetFlags()[13] ? this.organismPart : (org.ga4gh.models.OntologyTerm) defaultValue(fields()[13]);
        record.info = fieldSetFlags()[14] ? this.info : (java.util.Map<java.lang.CharSequence,java.util.List<java.lang.CharSequence>>) defaultValue(fields()[14]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
