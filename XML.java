import tester.*;

// represents a list of documents
interface ILoXMLFrag {
  // computes the length (number of characters) in an 
  // XML document. Tags and attributes have no contribution
  // to this length
  int contentLength();
  // does this XML document have a tag with the given name?
  boolean hasTag(String that);
  // does this XML document have an Attribute with the same name
  // as the given String?
  boolean hasAttribute(String that);
  // does this XML document have an Attribute with the given 
  // name within the given Tag name?
  boolean hasAttributeinTag(String att, String tag);
  // converts XML to a String without Tags or Attributes
  String renderAsString();
  // updates this XML's attributes with the given name and value
  ILoXMLFrag updateAttribute(String name, String value);


}

// represents an empty list of documents
class MtLoXMLFrag implements ILoXMLFrag {
  // computes the length (number of characters) in an 
  // XML document. Tags and attributes have no contribution
  // to this length
  public int contentLength() {
    return 0;
  }
  // does this XML document have a tag with the given name?
  public boolean hasTag(String that){
    return false;
  }
  // does this XML document have an Attribute with the same name
  // as the given String?
  public boolean hasAttribute(String that) {
    return false;
  }
  // does this XML document have an Attribute with the given 
  // name within the given Tag name?
  public boolean hasAttributeinTag(String att, String tag) {
    return false;
  }
  // converts XML to a String without Tags or Attributes
  public String renderAsString() {
    return "";
  }
  // updates this XML's attributes with the given name and value
  public ILoXMLFrag updateAttribute(String name, String value) {
    return this;
  }
}

// represents a list of documents with at least one XMLFrag
class ConsLoXMLFrag implements ILoXMLFrag {
  IXMLFrag first;
  ILoXMLFrag document;

  // the constructor
  ConsLoXMLFrag(IXMLFrag first, ILoXMLFrag document) {
    this.first = first;
    this.document = document;
  }
  // computes the length (number of characters) in an 
  // XML document. Tags and attributes have no contribution
  // to this length
  public int contentLength() {
    return this.first.contentLengthHelp() + this.document.contentLength();
  }
  // does this XML document have a tag with the given name?
  public boolean hasTag(String that){
    return this.first.hasTagHelpOne(that) || this.document.hasTag(that);
  }
  // does this XML document have an Attribute with the same name
  // as the given String?
  public boolean hasAttribute(String that) {
    return this.first.hasAttributeHelpOne(that) || this.document.hasAttribute(that);
  }
  // does this XML document have an Attribute with the given 
  // name within the given Tag name?
  public boolean hasAttributeinTag(String att, String tag) {
    return this.first.hasAttinTagHelpOne(att, tag) || this.document.hasAttributeinTag(att, tag);
  }
  // converts XML to a String without Tags or Attributes
  public String renderAsString() {
    return this.first.renderAsStringHelpOne() + this.document.renderAsString();
  }
  // updates this XML's attributes with the given name and value
  public ILoXMLFrag updateAttribute(String name, String value) {
    return new ConsLoXMLFrag(this.first.updateAttHelpOne(name, value), this.document.updateAttribute(name, value));
  }
}

// represents a document
interface IXMLFrag {
  // determines the length of the IXMLFrag, but
  // does not include Tags or Attributes
  int contentLengthHelp();
  // does this IXMLFrag have a Tag with the same
  // name as the string given?
  boolean hasTagHelpOne(String that);
  // does this IXMLFrag have an Attribute with the same
  // Attribute as the one with the given name?
  boolean hasAttributeHelpOne(String that);
  // does this IXMLFrag have an Attribute with the same
  // name in the given Tag?
  boolean hasAttinTagHelpOne(String att, String tag);
  // converts XML to a String without Tags or Attributes
  String renderAsStringHelpOne();
  // updates this XML's attributes with the given name and value
  IXMLFrag updateAttHelpOne(String name, String value);

}

// represents a document with just plaintext
class Plaintext implements IXMLFrag {
  String txt;

  Plaintext(String txt) {
    this.txt = txt;
  }

  // determines the length of the IXMLFrag, but
  // does not include Tags or Attributes
  public int contentLengthHelp() {
    return this.txt.length();
  }
  // does this IXMLFrag have a Tag with the same
  // name as the string given?
  public boolean hasTagHelpOne(String that) {
    return false;
  }
  // does this IXMLFrag have an Attribute with the same
  // Attribute as the one with the given name?
  public boolean hasAttributeHelpOne(String that) {
    return false;
  }
  // does this IXMLFrag have an Attribute with the same
  // name in the given Tag?
  public boolean hasAttinTagHelpOne(String att, String tag) {
    return false;
  }
  // converts XML to a String without Tags or Attributes
  public String renderAsStringHelpOne() {
    return this.txt.toString();
  }
  // updates this XML's attributes with the given name and value
  public IXMLFrag updateAttHelpOne(String name, String value) {
    return this;
  }

}

// represents a document with at least one Tag
class Tagged implements IXMLFrag {
  Tag tag;
  ILoXMLFrag content;

  Tagged(Tag tag, ILoXMLFrag content) {
    this.tag = tag;
    this.content = content;
  }

  // determines the length of the IXMLFrag, but
  // does not include Tags or Attributes
  public int contentLengthHelp() {
    return this.content.contentLength();
  }
  // does this IXMLFrag have a Tag with the same
  // name as the string given?
  public boolean hasTagHelpOne(String that) {
    return this.tag.hasTagHelpTwo(that) || this.content.hasTag(that);
  }
  // does this IXMLFrag have an Attribute with the same
  // Attribute as the one with the given name?
  public boolean hasAttributeHelpOne(String that) {
    return this.tag.hasAttributeHelpTwo(that) || this.content.hasAttribute(that);
  }
  // does this IXMLFrag have an Attribute with the same
  // name in the given Tag?
  public boolean hasAttinTagHelpOne(String att, String tag) {
    return this.tag.hasAttinTagHelpTwo(att, tag) || this.content.hasAttributeinTag(att, tag);
  }
  // converts XML to a String without Tags or Attributes
  public String renderAsStringHelpOne() {
    return this.content.renderAsString();
  }
  // updates this XML's attributes with the given name and value
  public IXMLFrag updateAttHelpOne(String name, String value) {
    return new Tagged(this.tag.updateAttHelpTwo(name, value), this.content.updateAttribute(name, value));
  }

}

// represents a Tag with a name and a List of Attributes
class Tag {
  String name;
  ILoAtt atts;

  Tag(String name, ILoAtt atts) {
    this.name = name;
    this.atts = atts;
  }

  // does this Tag have the same name as the string given?
  boolean hasTagHelpTwo(String that) {
    return this.name.equals(that);
  }
  // does this Tag have an Attribute with the same name as
  // the given name?
  boolean hasAttributeHelpTwo(String that) {
    return this.atts.hasAttributeHelpThree(that);
  }
  // does this Tag have the same name as the given one?
  boolean hasAttinTagHelpTwo(String att, String tag) {
    if (this.name.equals(tag)) {
      return this.atts.hasAttributeHelpThree(att);
    }
    else {
      return false;
    }
  }
  // updates this XML's attributes with the given name and value
  public Tag updateAttHelpTwo(String name, String value) {
    return new Tag(this.name, this.atts.updateAttHelpThree(name, value));
  }

}

// represents a list of Attributes
interface ILoAtt {
  // does this Attribute have the same name as the string given?
  boolean hasAttributeHelpThree(String that);
  // updates this XML's attributes with the given name and value
  ILoAtt updateAttHelpThree(String name, String value);

}

// represents an empty list of Attributes 
class MtLoAtt implements ILoAtt {
  // does this Attribute have the same name as the string given?
  public boolean hasAttributeHelpThree(String that) {
    return false;
  }
  //updates this XML's attributes with the given name and value
  public ILoAtt updateAttHelpThree(String name, String value) {
    return this;
  }
}

// represents a list of Attributes with at least one Attribute
class ConsLoAtt implements ILoAtt {
  Att att;
  ILoAtt rest;

  ConsLoAtt(Att att, ILoAtt rest) {
    this.att = att;
    this.rest = rest;
  }

  // does this Attribute have the same name as the string given?
  public boolean hasAttributeHelpThree(String that) {
    return this.att.hasAttributeHelpFour(that) || this.rest.hasAttributeHelpThree(that);
  }
  //updates this XML's attributes with the given name and value
  public ILoAtt updateAttHelpThree(String name, String value) {
    return new ConsLoAtt(this.att.updateAttHelpFour(name, value), this.rest.updateAttHelpThree(name, value));
  }
}

// represents an Attribute
class Att {
  String name;
  String value;

  Att(String name, String value) {
    this.name = name;
    this.value = value;
  }

  // does this Attribute have the same name as the string given?
  boolean hasAttributeHelpFour(String that) {
    return this.name.equals(that);
  }
  //updates this XML's attributes with the given name to the given value
  public Att updateAttHelpFour(String name, String value) {
    if (this.name.equals(name)) {
      return new Att(this.name, value);
    }
    else {
      return this;
    }
  }

}



// examples of XML
class ExamplesXML {
  // examples of Attributes
  Att volumeAtt = new Att("volume", "30db");
  Att durationAtt = new Att("duration", "5sec");

  // examples of Tags
  Tag yellTag = new Tag("yell", new MtLoAtt());                  // <yell>...</yell>
  Tag italicTag = new Tag("italic", new MtLoAtt());              // <italic>...</italic>
  Tag yell2Tag = new Tag("yell", new ConsLoAtt(this.volumeAtt,   // <yell volume="30db">...</yell>
      new MtLoAtt()));
  Tag yell3Tag = new Tag("yell", new ConsLoAtt(this.volumeAtt,   // <yell volume="30db" duration="5sec">...</yell>
      new ConsLoAtt(this.durationAtt, 
          new MtLoAtt())));

  // examples of ILoAtt
  ILoAtt empty_loatt = new MtLoAtt();
  ILoAtt volume_loatt = new ConsLoAtt(this.volumeAtt, new MtLoAtt());
  ILoAtt duration_loatt = new ConsLoAtt(this.durationAtt, new MtLoAtt());
  ILoAtt vthend_loatt = new ConsLoAtt(this.volumeAtt, new ConsLoAtt(this.durationAtt, new MtLoAtt()));
  ILoAtt dthenv_loatt = new ConsLoAtt(this.durationAtt, new ConsLoAtt(this.volumeAtt, new MtLoAtt()));

  // examples of IXMLFrag
  IXMLFrag plaintext = new Plaintext("I am XML!");
  IXMLFrag tagged = new Tagged(this.italicTag, 
      new MtLoXMLFrag());
  IXMLFrag complex = new Tagged(this.yell2Tag, 
      new ConsLoXMLFrag(new Plaintext("I am XML!"), 
          new MtLoXMLFrag()));

  // examples of XML
  ILoXMLFrag empty = new MtLoXMLFrag();                        // empty ILoXMLFrag
  ILoXMLFrag xml1 = new ConsLoXMLFrag(new Plaintext("I "),     // "I am XML!"
      new ConsLoXMLFrag(new Plaintext("am "), 
          new ConsLoXMLFrag(new Plaintext("XML"),
              new ConsLoXMLFrag(new Plaintext("!"), 
                  new MtLoXMLFrag()))));
  ILoXMLFrag xml2 = new ConsLoXMLFrag(new Plaintext("I "),    // "I am <yell>XML</yell>!"
      new ConsLoXMLFrag(new Plaintext("am "), 
          new ConsLoXMLFrag(new Tagged(this.yellTag, 
              new ConsLoXMLFrag(new Plaintext("XML"), new MtLoXMLFrag())), 
              new ConsLoXMLFrag(new Plaintext("!"), 
                  new MtLoXMLFrag()))));
  ILoXMLFrag xml3 = new ConsLoXMLFrag(new Plaintext("I "),    // "I am <yell><italic>X</italic>ML</yell>!"
      new ConsLoXMLFrag(new Plaintext("am "),
          new ConsLoXMLFrag(new Tagged(this.yellTag, 
              new ConsLoXMLFrag(new Tagged(this.italicTag, 
                  new ConsLoXMLFrag(new Plaintext("X"), new MtLoXMLFrag())), 
                  new ConsLoXMLFrag(new Plaintext("ML"), new MtLoXMLFrag()))), 
              new ConsLoXMLFrag(new Plaintext("!"), new MtLoXMLFrag()))));  
  ILoXMLFrag xml4 = new ConsLoXMLFrag(new Plaintext("I "),   // "I am <yell volume="30db"><italic>X</italic>ML</yell>!"
      new ConsLoXMLFrag(new Plaintext("am "),
          new ConsLoXMLFrag(new Tagged(this.yell2Tag,
              new ConsLoXMLFrag(new Tagged(this.italicTag,
                  new ConsLoXMLFrag(new Plaintext("X"), new MtLoXMLFrag())),
                  new ConsLoXMLFrag(new Plaintext("ML"), new MtLoXMLFrag()))), 
              new ConsLoXMLFrag(new Plaintext("!"), new MtLoXMLFrag()))));
  ILoXMLFrag xml5 = new ConsLoXMLFrag(new Plaintext("I "),   // "I am <yell volume="30db" duration="5sec"><italic>X</italic>ML</yell>!"
      new ConsLoXMLFrag(new Plaintext("am "),
          new ConsLoXMLFrag(new Tagged(this.yell3Tag,
              new ConsLoXMLFrag(new Tagged(this.italicTag,
                  new ConsLoXMLFrag(new Plaintext("X"), new MtLoXMLFrag())), 
                  new ConsLoXMLFrag(new Plaintext("ML"), new MtLoXMLFrag()))),
              new ConsLoXMLFrag(new Plaintext("!"), new MtLoXMLFrag()))));


  // tests the contentLengthHelp() method, a helper for contentLength()
  void testContentLengthHelp(Tester t) {
    t.checkExpect(this.plaintext.contentLengthHelp(), 9);

    t.checkExpect(this.tagged.contentLengthHelp(), 0);

    t.checkExpect(this.complex.contentLengthHelp(), 9);

    t.checkExpect(this.plaintext.contentLengthHelp() == this.complex.contentLengthHelp(), true);
  }

  // tests the contentLength() method
  void testContentLength(Tester t) {
    t.checkExpect(this.empty.contentLength(), 0);

    t.checkExpect(this.xml1.contentLength(), 9);

    t.checkExpect(this.xml2.contentLength(), 9);

    t.checkExpect(this.xml3.contentLength(), 9);

    t.checkExpect(this.xml4.contentLength(), 9);
    t.checkExpect(this.xml4.contentLength(), 9);
  }

  // tests the hasTagHelpTwo(String) method, a helper for hasTagHelpOne(String)
  void testHasTagHelpTwo(Tester t) {
    t.checkExpect(this.italicTag.hasTagHelpTwo("italic"), true);
    t.checkExpect(this.italicTag.hasTagHelpTwo("yell"), false);
    t.checkExpect(this.italicTag.hasTagHelpTwo("hello"), false);

    t.checkExpect(this.yellTag.hasTagHelpTwo("yell"), true);
    t.checkExpect(this.yellTag.hasTagHelpTwo("italic"), false);
    t.checkExpect(this.yellTag.hasTagHelpTwo("world"), false);

    t.checkExpect(this.yell2Tag.hasTagHelpTwo("yell"), true);
    t.checkExpect(this.yell2Tag.hasTagHelpTwo("hello"), false);

    t.checkExpect(this.yell3Tag.hasTagHelpTwo("yell"), true);
    t.checkExpect(this.yell3Tag.hasTagHelpTwo("world"), false);
  }

  // tests the hasTagHelpOne(String) method, a helper for hasTag(String)
  void testHasTagHelpOne(Tester t) {
    t.checkExpect(this.plaintext.hasTagHelpOne("italic"), false);
    t.checkExpect(this.plaintext.hasTagHelpOne("yell"), false);

    t.checkExpect(this.tagged.hasTagHelpOne("italic"), true);
    t.checkExpect(this.tagged.hasTagHelpOne("yell"), false);

    t.checkExpect(this.complex.hasTagHelpOne("italic"), false);
    t.checkExpect(this.complex.hasTagHelpOne("yell"), true);
  }

  // tests the hasTag(String) method
  void testHasTag(Tester t) {
    t.checkExpect(this.empty.hasTag("italic"), false);
    t.checkExpect(this.empty.hasTag("yell"), false);

    t.checkExpect(this.xml1.hasTag("italic"), false);
    t.checkExpect(this.xml1.hasTag("yell"), false);

    t.checkExpect(this.xml2.hasTag("italic"), false);
    t.checkExpect(this.xml2.hasTag("yell"), true);

    t.checkExpect(this.xml3.hasTag("italic"), true);
    t.checkExpect(this.xml3.hasTag("yell"), true);

    t.checkExpect(this.xml4.hasTag("italic"), true);
    t.checkExpect(this.xml4.hasTag("yell"), true);

    t.checkExpect(this.xml5.hasTag("italic"), true);
    t.checkExpect(this.xml5.hasTag("yell"), true);
  }

  // tests the hasAttributeHelpFour(String) method, a helper for hasAttributeHelpThree(String)
  void testHasAttributeHelpFour(Tester t) {
    t.checkExpect(this.volumeAtt.hasAttributeHelpFour("volume"), true);
    t.checkExpect(this.volumeAtt.hasAttributeHelpFour("duration"), false);

    t.checkExpect(this.durationAtt.hasAttributeHelpFour("duration"), true);
    t.checkExpect(this.durationAtt.hasAttributeHelpFour("volume"), false);
  }

  // tests the hasAttributeHelpThree(String) method, a helper for hasAttributeHelpTwo(String)
  void testHasAttributeHelpThree(Tester t) {
    t.checkExpect(this.empty_loatt.hasAttributeHelpThree("volume"), false);
    t.checkExpect(this.empty_loatt.hasAttributeHelpThree("duration"), false);

    t.checkExpect(this.volume_loatt.hasAttributeHelpThree("volume"), true);
    t.checkExpect(this.volume_loatt.hasAttributeHelpThree("duration"), false);

    t.checkExpect(this.duration_loatt.hasAttributeHelpThree("volume"), false);
    t.checkExpect(this.duration_loatt.hasAttributeHelpThree("duration"), true);

    t.checkExpect(this.vthend_loatt.hasAttributeHelpThree("volume"), true);
    t.checkExpect(this.vthend_loatt.hasAttributeHelpThree("duration"), true);

    t.checkExpect(this.dthenv_loatt.hasAttributeHelpThree("volume"), true);
    t.checkExpect(this.dthenv_loatt.hasAttributeHelpThree("duration"), true);
  }

  // tests the hasAttributeHelpTwo(String) method, a helper for hasAttributeHelpOne(String)
  void testHasAttributeHelpTwo(Tester t) {
    t.checkExpect(this.yellTag.hasAttributeHelpTwo("volume"), false);
    t.checkExpect(this.yellTag.hasAttributeHelpTwo("duration"), false);

    t.checkExpect(this.italicTag.hasAttributeHelpTwo("volume"), false);
    t.checkExpect(this.italicTag.hasAttributeHelpTwo("duration"), false);

    t.checkExpect(this.yell2Tag.hasAttributeHelpTwo("volume"), true);
    t.checkExpect(this.yell2Tag.hasAttributeHelpTwo("duration"), false);

    t.checkExpect(this.yell3Tag.hasAttributeHelpTwo("volume"), true);
    t.checkExpect(this.yell3Tag.hasAttributeHelpTwo("duration"), true);
  }

  // tests the hasAttributeHelpOne(String) method, a helper for hasAttribute(String)
  void testHasAttributeHelpOne(Tester t) {
    t.checkExpect(this.plaintext.hasAttributeHelpOne("volume"), false);
    t.checkExpect(this.plaintext.hasAttributeHelpOne("duration"), false);

    t.checkExpect(this.tagged.hasAttributeHelpOne("volume"), false);
    t.checkExpect(this.tagged.hasAttributeHelpOne("duration"), false);

    t.checkExpect(this.complex.hasAttributeHelpOne("volume"), true);
    t.checkExpect(this.complex.hasAttributeHelpOne("duration"), false);
  }

  // tests the hasAttribute(String) method
  void testHasAttribute(Tester t) {
    t.checkExpect(this.empty.hasAttribute("volume"), false);
    t.checkExpect(this.empty.hasAttribute("duration"), false);

    t.checkExpect(this.xml1.hasAttribute("volume"), false);
    t.checkExpect(this.xml1.hasAttribute("duration"), false);

    t.checkExpect(this.xml2.hasAttribute("volume"), false);
    t.checkExpect(this.xml2.hasAttribute("duration"), false);

    t.checkExpect(this.xml3.hasAttribute("volume"), false);
    t.checkExpect(this.xml3.hasAttribute("duration"), false);

    t.checkExpect(this.xml4.hasAttribute("volume"), true);
    t.checkExpect(this.xml4.hasAttribute("duration"), false);

    t.checkExpect(this.xml5.hasAttribute("volume"), true);
    t.checkExpect(this.xml5.hasAttribute("duration"), true);
  }

  // tests the hasAttinTagHelpTwo(String, String) method, a helper for hasAttinTag(String, String) method
  void testHasAttinTagHelpTwo(Tester t) {
    t.checkExpect(this.yellTag.hasAttinTagHelpTwo("volume", "yell"), false);
    t.checkExpect(this.yellTag.hasAttinTagHelpTwo("duration", "yell"), false);
    t.checkExpect(this.yellTag.hasAttinTagHelpTwo("volume", "italic"), false);
    t.checkExpect(this.yellTag.hasAttinTagHelpTwo("duration", "italic"), false);

    t.checkExpect(this.italicTag.hasAttinTagHelpTwo("volume", "yell"), false);
    t.checkExpect(this.italicTag.hasAttinTagHelpTwo("duration", "yell"), false);
    t.checkExpect(this.italicTag.hasAttinTagHelpTwo("volume", "italic"), false);
    t.checkExpect(this.italicTag.hasAttinTagHelpTwo("duration", "italic"), false);

    t.checkExpect(this.yell2Tag.hasAttinTagHelpTwo("volume", "yell"), true);
    t.checkExpect(this.yell2Tag.hasAttinTagHelpTwo("duration", "yell"), false);
    t.checkExpect(this.yell2Tag.hasAttinTagHelpTwo("volume", "italic"), false);
    t.checkExpect(this.yell2Tag.hasAttinTagHelpTwo("duration", "italic"), false);

    t.checkExpect(this.yell3Tag.hasAttinTagHelpTwo("volume", "yell"), true);
    t.checkExpect(this.yell3Tag.hasAttinTagHelpTwo("duration", "yell"), true);
    t.checkExpect(this.yell3Tag.hasAttinTagHelpTwo("volume", "italic"), false);
    t.checkExpect(this.yell3Tag.hasAttinTagHelpTwo("duration", "italic"), false);
  }

  // tests the hasAttinTagHelpOne(String, String) method, a helper for hasAttributeinTag(String, String) method
  void testHasAttinTagHelpOne(Tester t) {
    t.checkExpect(this.plaintext.hasAttinTagHelpOne("volume", "yell"), false);
    t.checkExpect(this.plaintext.hasAttinTagHelpOne("duration", "yell"), false);
    t.checkExpect(this.plaintext.hasAttinTagHelpOne("volume", "italic"), false);
    t.checkExpect(this.plaintext.hasAttinTagHelpOne("duration", "italic"), false);

    t.checkExpect(this.tagged.hasAttinTagHelpOne("volume", "yell"), false);
    t.checkExpect(this.tagged.hasAttinTagHelpOne("duration", "yell"), false);
    t.checkExpect(this.tagged.hasAttinTagHelpOne("volume", "italic"), false);
    t.checkExpect(this.tagged.hasAttinTagHelpOne("duration", "italic"), false);

    t.checkExpect(this.complex.hasAttinTagHelpOne("volume", "yell"), true);
    t.checkExpect(this.complex.hasAttinTagHelpOne("duration", "yell"), false);
    t.checkExpect(this.complex.hasAttinTagHelpOne("volume", "italic"), false);
    t.checkExpect(this.complex.hasAttinTagHelpOne("duration", "italic"), false);
  }

  // tests the hasAttributeinTag(String, String) method
  void testHasAttributeinTag(Tester t) {
    t.checkExpect(this.empty.hasAttributeinTag("volume", "yell"), false);
    t.checkExpect(this.empty.hasAttributeinTag("duration", "yell"), false);
    t.checkExpect(this.empty.hasAttributeinTag("volume", "italic"), false);
    t.checkExpect(this.empty.hasAttributeinTag("duration", "italic"), false);

    t.checkExpect(this.xml1.hasAttributeinTag("volume", "yell"), false);
    t.checkExpect(this.xml1.hasAttributeinTag("duration", "yell"), false);
    t.checkExpect(this.xml1.hasAttributeinTag("volume", "italic"), false);
    t.checkExpect(this.xml1.hasAttributeinTag("duration", "italic"), false);

    t.checkExpect(this.xml2.hasAttributeinTag("volume", "yell"), false);
    t.checkExpect(this.xml2.hasAttributeinTag("duration", "yell"), false);
    t.checkExpect(this.xml2.hasAttributeinTag("volume", "italic"), false);
    t.checkExpect(this.xml2.hasAttributeinTag("duration", "italic"), false);

    t.checkExpect(this.xml3.hasAttributeinTag("volume", "yell"), false);
    t.checkExpect(this.xml3.hasAttributeinTag("duration", "yell"), false);
    t.checkExpect(this.xml3.hasAttributeinTag("volume", "italic"), false);
    t.checkExpect(this.xml3.hasAttributeinTag("duration", "italic"), false);

    t.checkExpect(this.xml4.hasAttributeinTag("volume", "yell"), true);
    t.checkExpect(this.xml4.hasAttributeinTag("duration", "yell"), false);
    t.checkExpect(this.xml4.hasAttributeinTag("volume", "italic"), false);
    t.checkExpect(this.xml4.hasAttributeinTag("duration", "italic"), false);

    t.checkExpect(this.xml5.hasAttributeinTag("volume", "yell"), true);
    t.checkExpect(this.xml5.hasAttributeinTag("duration", "yell"), true);
    t.checkExpect(this.xml5.hasAttributeinTag("volume", "italic"), false);
    t.checkExpect(this.xml5.hasAttributeinTag("duration", "italic"), false);
  }

  // tests the renderAsStringHelpOne() method, a helper for renderAsString() method
  void testRenderAsStringHelpOne(Tester t) {
    t.checkExpect(this.plaintext.renderAsStringHelpOne(), "I am XML!");
    t.checkExpect(this.tagged.renderAsStringHelpOne(), "");
    t.checkExpect(this.complex.renderAsStringHelpOne(), "I am XML!");
  }


  // tests the renderAsString() method
  void testRenderAsString(Tester t) {
    t.checkExpect(this.empty.renderAsString(), "");
    t.checkExpect(this.xml1.renderAsString(), "I am XML!");
    t.checkExpect(this.xml2.renderAsString(), "I am XML!");
    t.checkExpect(this.xml3.renderAsString(), "I am XML!");
    t.checkExpect(this.xml4.renderAsString(), "I am XML!");
    t.checkExpect(this.xml5.renderAsString(), "I am XML!");
  }

  // tests the updateAttHelpFour(name, value) method, a helper for updateAttribute(name, value)
  void testUpdateAttHelpFour(Tester t) {
    t.checkExpect(this.volumeAtt.updateAttHelpFour("volume", "5miles"), new Att("volume", "5miles"));
    t.checkExpect(this.volumeAtt.updateAttHelpFour("duration", "45db"), this.volumeAtt);
    t.checkExpect(this.durationAtt.updateAttHelpFour("duration", "30sec"), new Att("duration", "30sec"));
    t.checkExpect(this.durationAtt.updateAttHelpFour("volume", "7meters"), this.durationAtt);
  }

  // tests the updateAttHelpThree(name, value) method, a helper for updateAttribute(name, value)
  void testUpdateAttHelpThree(Tester t) {
    t.checkExpect(this.empty_loatt.updateAttHelpThree("volume", "5miles"), this.empty_loatt);

    t.checkExpect(this.volume_loatt.updateAttHelpThree("volume", "10dec"), 
        new ConsLoAtt(new Att("volume", "10dec"), new MtLoAtt()));
    t.checkExpect(this.volume_loatt.updateAttHelpThree("duration", "10dec"), 
        new ConsLoAtt(this.volumeAtt, new MtLoAtt()));

    t.checkExpect(this.duration_loatt.updateAttHelpThree("duration", "3db"), 
        new ConsLoAtt(new Att("duration", "3db"), new MtLoAtt()));
    t.checkExpect(this.duration_loatt.updateAttHelpThree("volume", "3db"), 
        new ConsLoAtt(this.durationAtt, new MtLoAtt()));

    t.checkExpect(this.vthend_loatt.updateAttHelpThree("volume", "90db"), 
        new ConsLoAtt(new Att("volume", "90db"), new ConsLoAtt(this.durationAtt, new MtLoAtt())));
    t.checkExpect(this.vthend_loatt.updateAttHelpThree("duration", "1sec"), 
        new ConsLoAtt(this.volumeAtt, new ConsLoAtt(new Att("duration", "1sec"), new MtLoAtt())));
    t.checkExpect(this.vthend_loatt.updateAttHelpThree("hello", "90db"), 
        new ConsLoAtt(this.volumeAtt, new ConsLoAtt(this.durationAtt, new MtLoAtt())));

    t.checkExpect(this.dthenv_loatt.updateAttHelpThree("volume", "90db"), 
        new ConsLoAtt(this.durationAtt, new ConsLoAtt(new Att("volume", "90db"), new MtLoAtt())));
    t.checkExpect(this.dthenv_loatt.updateAttHelpThree("duration", "1sec"), 
        new ConsLoAtt(new Att("duration", "1sec"), new ConsLoAtt(this.volumeAtt, new MtLoAtt())));
    t.checkExpect(this.dthenv_loatt.updateAttHelpThree("hello", "90db"), 
        new ConsLoAtt(this.durationAtt, new ConsLoAtt(this.volumeAtt, new MtLoAtt())));
  }

  // tests the updateAttHelpTwo(name, value) method, a helper for updateAttribute(name, value)
  void testUpdateAppHelpTwo(Tester t) { 
    t.checkExpect(this.yellTag.updateAttHelpTwo("volume", "20db"), this.yellTag);
    t.checkExpect(this.yellTag.updateAttHelpTwo("duration", "20sec"), this.yellTag);

    t.checkExpect(this.italicTag.updateAttHelpTwo("volume", "20db"), this.italicTag);
    t.checkExpect(this.italicTag.updateAttHelpTwo("duration", "20sec"), this.italicTag);

    t.checkExpect(this.yell2Tag.updateAttHelpTwo("volume", "20db"), 
        new Tag("yell", new ConsLoAtt(new Att("volume", "20db"), new MtLoAtt())));
    t.checkExpect(this.yell2Tag.updateAttHelpTwo("duration", "20sec"), this.yell2Tag);

    t.checkExpect(this.yell3Tag.updateAttHelpTwo("volume", "20db"), 
        new Tag("yell", new ConsLoAtt(new Att("volume", "20db"), new ConsLoAtt(this.durationAtt, new MtLoAtt()))));
    t.checkExpect(this.yell3Tag.updateAttHelpTwo("duration", "20sec"), 
        new Tag("yell", new ConsLoAtt(this.volumeAtt, new ConsLoAtt(new Att("duration", "20sec"), new MtLoAtt()))));
  }

  // tests the updateAttHelpOne(name, value) method, a helper for updateAttribute(name, value)
  void testUpdateAppHelpOne(Tester t) {
    t.checkExpect(this.plaintext.updateAttHelpOne("volume", "20db"), this.plaintext);
    t.checkExpect(this.plaintext.updateAttHelpOne("duration", "20sec"), this.plaintext);

    t.checkExpect(this.tagged.updateAttHelpOne("volume", "20db"), this.tagged);
    t.checkExpect(this.tagged.updateAttHelpOne("duration", "20sec"), this.tagged);

    t.checkExpect(this.complex.updateAttHelpOne("volume", "20db"), 
        new Tagged(new Tag("yell", new ConsLoAtt(new Att("volume", "20db"), new MtLoAtt())), 
            new ConsLoXMLFrag(new Plaintext("I am XML!"), 
                new MtLoXMLFrag())));
    t.checkExpect(this.complex.updateAttHelpOne("duration", "20sec"), this.complex);
  }

  // tests the updateAttribute(name, value) method
  void testUpdateAttribute(Tester t) {
    t.checkExpect(this.empty.updateAttribute("volume", "20db"), this.empty);
    t.checkExpect(this.empty.updateAttribute("duration", "20sec"), this.empty);

    t.checkExpect(this.xml1.updateAttribute("volume", "20db"), this.xml1);
    t.checkExpect(this.xml1.updateAttribute("duration", "20sec"), this.xml1);

    t.checkExpect(this.xml2.updateAttribute("volume", "20db"), this.xml2);
    t.checkExpect(this.xml2.updateAttribute("duration", "20sec"), this.xml2);

    t.checkExpect(this.xml3.updateAttribute("volume", "20db"), this.xml3);
    t.checkExpect(this.xml3.updateAttribute("duration", "20sec"), this.xml3);

    t.checkExpect(this.xml4.updateAttribute("volume", "20db"),
        new ConsLoXMLFrag(new Plaintext("I "), 
            new ConsLoXMLFrag(new Plaintext("am "), 
                new ConsLoXMLFrag(new Tagged(new Tag("yell", 
                    new ConsLoAtt(new Att("volume", "20db"), new MtLoAtt())), 
                    new ConsLoXMLFrag(new Tagged(this.italicTag, 
                        new ConsLoXMLFrag(new Plaintext("X"), new MtLoXMLFrag())), 
                        new ConsLoXMLFrag(new Plaintext("ML"), new MtLoXMLFrag()))), 
                    new ConsLoXMLFrag(new Plaintext("!"), new MtLoXMLFrag())))));
    t.checkExpect(this.xml4.updateAttribute("duration", "20sec"), this.xml4);

    t.checkExpect(this.xml5.updateAttribute("volume", "20db"),
        new ConsLoXMLFrag(new Plaintext("I "), 
            new ConsLoXMLFrag(new Plaintext("am "),
                new ConsLoXMLFrag(new Tagged(new Tag("yell",
                    new ConsLoAtt(new Att("volume", "20db"),
                        new ConsLoAtt(this.durationAtt, new MtLoAtt()))),
                    new ConsLoXMLFrag(new Tagged(this.italicTag,
                        new ConsLoXMLFrag(new Plaintext("X"), new MtLoXMLFrag())), 
                        new ConsLoXMLFrag(new Plaintext("ML"), new MtLoXMLFrag()))),
                    new ConsLoXMLFrag(new Plaintext("!"), new MtLoXMLFrag())))));
    t.checkExpect(this.xml5.updateAttribute("duration", "20sec"),
        new ConsLoXMLFrag(new Plaintext("I "), 
            new ConsLoXMLFrag(new Plaintext("am "),
                new ConsLoXMLFrag(new Tagged(new Tag("yell",
                    new ConsLoAtt(this.volumeAtt,
                        new ConsLoAtt(new Att("duration", "20sec"), new MtLoAtt()))),
                    new ConsLoXMLFrag(new Tagged(this.italicTag,
                        new ConsLoXMLFrag(new Plaintext("X"), new MtLoXMLFrag())), 
                        new ConsLoXMLFrag(new Plaintext("ML"), new MtLoXMLFrag()))),
                    new ConsLoXMLFrag(new Plaintext("!"), new MtLoXMLFrag())))));
  }



}