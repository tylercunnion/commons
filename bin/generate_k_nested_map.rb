require 'fileutils'

$numbers = ["Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
           "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen", "Twenty"]

$classNames = $numbers.map { |number| (number.eql?("Zero") || number.eql?("One"))? "Map" : number + "NestedMap"}
#if you need a nested map with more than this many levels, you may want to rethink your life decisions
#but if you really need it, you can add however many numbers to the array. the code should still work

k = ARGV[0].to_i
output_path = ARGV[1]
package = output_path.split("/").drop_while{|p| p != "java"}.drop(1).join(".")



def getGets(k)
  methods = []
  firstMethod = []
  generics = getGenerics((2..k))
  className = $classNames[k-1]
  firstMethod << "  public #{$classNames[k-1]}<#{getGenerics(2..k)}, V> get(K1 k1) {"
  firstMethod << "    return (data.get(k1) == null) ? new #{(k == 2) ? "HashMap<#{generics}, V>()" : "#{className}<#{generics}, V>(defaultValueSupplier)"} : data.get(k1);"
  firstMethod << "  }"
  methods << firstMethod
  (2..k-1).each do |i|
    method = []
    returnType = "#{$classNames[k-i]}<#{getGenerics((i+1..k))}, V>"
    method << "  public #{returnType} get(#{getParams(1..i)}) {"
    method << "    return (data.get(k1) == null || data.get(k1).get(#{getArgs(2..i)}) == null) ? #{emptyCollection(returnType, i, k-1)} : data.get(k1).get(#{getArgs(2..i)});"
    method << "  }"
    methods << method
  end
  lastMethod = []
  lastMethod << "  public V get(#{getParams(1..k)}) {"
  lastMethod << "    return (data.get(k1) == null || data.get(k1).get(#{getArgs(2..k)}) == null) ? defaultValueSupplier.get() : data.get(k1).get(#{getArgs(2..k)});"
  lastMethod << "  }"
  methods << lastMethod;
  return methods
end
def getPuts(k)
  methods = []
  method = []
  method << "  public void put(#{getParams((1..k))}, V v) {"
  method << "    if(data.get(k1) == null) {"
  method << "      data.put(k1, new HashSet<#{getArgs(2..k)}>();"
  method << "    }"
  method << "    data.get(k1).put(#{getArgs(2..k)}, v);"
  method << "  }"
  methods << method
  return methods
end
def getSorts(k)
  sorts = []
  firstSort = []
  firstSort << "  public List<K1> key1Sort(Comparator<K1> sort) {"
  firstSort << "    List<K1> list = Lists.newArrayList(key1Set());"
  firstSort << "    Collections.sort(list, sort);"
  firstSort << "    return list;"
  firstSort << "  }"
  sorts << firstSort
  ((2..(k-1)).to_a).each do |i|
    sort = []
    generics = getGenerics((1..i))
    sort << "  public List<#{$numbers[i] + "KeyTuple"}<#{generics}>> key#{(1..i).to_a.join}Sort(Comparator<#{$numbers[i]}KeyTuple<#{generics}>> sort) {"
    sort << "    List<#{$numbers[i] + "KeyTuple"}<#{generics}>> list = Lists.newArrayList(key#{(1..i).to_a.join}Set());"
    sort << "    Collections.sort(list, sort);"
    sort << "    return list;"
    sort << "  }"
    sorts << sort
  end
  return sorts
end

def getKeySets(k)
  methods = []

  firstMethod = []
  firstMethod << "  public Set<K1> key1Set() {"
  firstMethod << "    if(data.keySet() != null) {"
  firstMethod << "      return data.keySet();"
  firstMethod << "    }"
  firstMethod << "    else return Collections.emptySet();"
  firstMethod << "  }"
  methods << firstMethod

  ((2..k).to_a).each do |i|
    method = []
    method << "  public Set<K#{i}> key#{i}Set(#{getParams((1..(i-1))).split(" ").join(" ")}) {"
    method << "    if(data.get(k1) != null) {"
    method << "      return data.get(k1).key#{(k == 2 && i == 2) ? "" : i-1}Set(#{getArgs((2..i-1).to_a)});"
    method << "    } else {"
    method << "    return Collections.emptySet();"
    method << "    }"
    method << "  }"
    methods << method
  end

  (1..k).to_a.combination(2).to_a.each do |pair|
    interval = (pair[0]..pair[1]).to_a
    method = []
    size = $numbers[interval.length]
    method << "  public Set<#{size}KeyTuple<#{getGenerics(interval)}>> key#{interval.join}Set(#{getParams((1..interval[0]-1).to_a)}) {"
    method << "    if(data.keySet().isEmpty()) {"
    method << "      return Collections.emptySet();"
    method << "    }"
    if interval[0] == 1 then
      method << "    Set<" + size + "KeyTuple<#{getGenerics(interval)}>> tuples = new HashSet<#{size}KeyTuple<#{getGenerics(interval)}>>();"
      method << "    for(K1 k1 : data.keySet()) {"
      interval.reverse.drop(1).reverse.each do |i|
        method << "#{(["  "] * (i + 2)).join}#{($classNames[k - i])}<#{getGenerics((i + 1..k))}, V> map#{i.to_s} = #{((i == 1) ? "data.get(k1)" : "map" + (i-1).to_s + ".get(k" + (i).to_s + ")")};"
        method << "#{(["  "] * (i + 2)).join}for(K#{(i + 1).to_s} k#{(i + 1).to_s}: map#{i.to_s}.key#{(k - i == 1) ? "" : 1}Set()) {"
      end
      method << "#{(["  "] * (interval.last + 2)).join}tuples.add(new #{size}KeyTuple<#{getGenerics(interval)}>(#{getArgs(interval)}));"
      interval.reverse.drop(1).each do |i|
        method << "#{(["  "] * (i + 2)).join}}"
      end
      method << "    }"
      method << "    return tuples;"
      method << "  }"
    else
      method << "    if(data.get(k1) != null) {"
      method << "      return data.get(k1).key#{interval.map {|i| i - 1}.join}Set(#{getArgs((2..interval[0] - 1).to_a)});"
      method << "    } else {"
      method << "      return Collections.emptySet();"
      method << "    }"
      method << "  }"
    end
    methods << method
  end
  return methods
end

def getParams(ary)
  params = ""
  ary.each do |number|
    params << "K" << number.to_s << " k" << number.to_s << ", "
  end
  return params.chop.chop
end

def getGenerics(ary)
  generics = ""
  ary.each do |number|
    generics << "K" << number.to_s << ", "
  end
  return generics.chop.chop
end

def getArgs(ary)
  args = ""
  ary.each do |number|
    args << "k" << number.to_s << ", "
  end
  return args.chop.chop
end

def emptyCollection(returnType, ary, max)
  ary == max ? "Collections.<#{getGenerics([ary+1])}, V>emptyMap()" : "new #{returnType}()"
end

for i in (2..k) do
  className = $numbers[i] + "NestedMap"
  lastClassName = (i > 2) ? $numbers[i - 1] + "NestedMap" : "Map"
  tupleName = $numbers[i] + "KeyTuple"
  lastTupleName = (i > 2) ? $numbers[i - 1] + "KeyTuple" : ""
  FileUtils.mkdir_p output_path
  newMapFile = File.open("#{output_path}/#{className}.java", "w+")
  newTupleFile = File.open("#{output_path}/#{tupleName}.java", "w+")


  newMapFile.puts("package #{package};

import java.util.*;
import java.io.Serializable;

import com.google.common.collect.Lists;

public class #{className}<#{getGenerics((1..i))}, V> implements Iterable<#{className}.Entry<#{getGenerics((1..i))}, V>>, Serializable {
  protected final Map<K1, #{lastClassName}<#{getGenerics((2..i))}, V>> data = new HashMap<K1, #{lastClassName}<#{getGenerics(2..i)}, V>>();
  private final com.google.common.base.Supplier<V> defaultValueSupplier;

  public #{className}(com.google.common.base.Supplier<V> defaultValueSupplier) {
    this.defaultValueSupplier = defaultValueSupplier;
  }

  /**
  * @deprecated stores instance, so every default value modified is the same
  */
  @Deprecated
  public #{className}(V defaultValue) {
    this(com.google.common.base.Suppliers.ofInstance(defaultValue));
  }

  public #{className}() {
    this((V)null);
  }

  public #{className}(#{className}<#{getGenerics(1..i)}, V> other){
    this.defaultValueSupplier = other.defaultValueSupplier;
    this.putAll(other);
  }
")
  newMapFile.puts(getKeySets(i).join("\n"))
  newMapFile.puts(getGets(i).join("\n"))
  newMapFile.puts("
  public V get(#{tupleName} tuple) {
    return data.get(tuple.head()).get(tuple.tail());
  }
")
  newMapFile.puts(getSorts(i).join("\n"))

  newMapFile.puts("
  public void put(#{getParams(1..i)}, V v) {
    if(data.get(k1) == null) {
      data.put(k1, new #{(i == 2) ? "HashMap" : lastClassName}<#{getGenerics(2..i)}, V>());
    }
    data.get(k1).put(#{getArgs(2..i)}, v);
  }

  public void clear() {
    data.clear();
  }

  public Collection<V> values() {
    Iterator<Entry<#{getGenerics(1..i)}, V>> iterator = iterator();
    List<V> values = new ArrayList();
    while(iterator.hasNext()) {
      values.add(iterator.next().getValue());
    }
    return values;
  }

  public boolean containsKey(#{getParams(1..i)}) {
    return data.containsKey(k1) && data.get(k1).containsKey(#{getArgs(2..i)});
  }

  public boolean containsKey(#{tupleName} key) {
    return data.containsKey(key.getK1()) && data.get(key.getK1()).containsKey(key.tail());
  }

  public boolean containsValue(V v) {
    return values().contains(v);
  }

  public boolean isEmpty() {
    return data.isEmpty();
  }

  public int size() {
    return entrySet().size();
  }

  public Set<Entry<#{getGenerics(1..i)}, V>> entrySet() {
    Set<#{tupleName}<#{getGenerics(1..i)}>> tuples = key#{(1..i).to_a.join}Set();
    Iterator<#{tupleName}<#{getGenerics(1..i)}>> i = tuples.iterator();
    Set<Entry<#{getGenerics(1..i)}, V>> entries = new HashSet<Entry<#{getGenerics(1..i)}, V>>();
    while(i.hasNext()) {
      #{tupleName}<#{getGenerics(1..i)}> next = i.next();
      entries.add(new Entry<#{getGenerics(1..i)}, V>(next, get(next)));
    }
    return entries;
  }

  public void putAll(#{className}<#{getGenerics((1..i))}, V> map){
    for(K1 key : map.key1Set()){
      #{lastClassName}<#{getGenerics((2..i))}, V> currentSubMap = this.get(key);
      currentSubMap.putAll(map.get(key));
      this.put(key, currentSubMap);
    }
  }

  public void put(K1 key, #{lastClassName}<#{getGenerics((2..i))}, V> map){
    data.put(key, map);
  }

  public Iterator<Entry<#{getGenerics(1..i)}, V>> iterator() {
    return entrySet().iterator();
  }

  @Override
  public String toString() {
    return \"#{$numbers[i]}NestedMap{\" +
        \"data=\" + data +
        \", defaultValueSupplier=\" + defaultValueSupplier +
        '}';
  }

  public static class Entry<#{getGenerics(1..i)}, V>  {
    private final #{tupleName}<#{getGenerics(1..i)}> keyTuple;
    private final V value;

    public Entry(#{getParams(1..i)}, V v) {
      keyTuple = new #{tupleName}<#{getGenerics(1..i)}>(#{getArgs(1..i)});
      value = v;
    }

    public Entry(#{tupleName}<#{getGenerics(1..i)}> tuple, V v) {
      keyTuple = tuple;
      value = v;
    }

    public #{tupleName}<#{getGenerics(1..i)}> getKeyTuple() {
      return keyTuple;
    }

    public #{tupleName}<#{getGenerics(1..i)}> getKey() {
      return keyTuple;
    }")

  (1..i).each do |j|
    newMapFile.puts("    public K#{j} getK#{j}() {
      return keyTuple.getK#{j}();
    }")
  end
  newMapFile.puts("

    public V getValue() {
      return value;
    }

    public int hashCode() {
      return keyTuple.hashCode() * 31 + value.hashCode();
    }

    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if(o == null || getClass() != o.getClass()) {
        return false;
      }
      Entry entry = (Entry) o;
      return (getKey().equals(entry.getKey()) && getValue().equals(entry.getValue()));
    }

    @Override
    public String toString() {
      return String.format(\"%s -> %s\", keyTuple.toList(), value);
    }

  }
}")


  newTupleFile.puts("package #{package};

import java.util.*;
import java.io.Serializable;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;


public class #{tupleName}<#{getGenerics(1..i)}> implements Serializable{")
  (1..i).each {|j| newTupleFile.puts("  private K" + j.to_s + " k" + j.to_s + ";\n")}
  newTupleFile.puts("  public #{tupleName}(#{getParams(1..i)}) {")
  (1..i).each {|j| newTupleFile.puts("    this.k" + j.to_s + " = k" + j.to_s + ";\n")}
  newTupleFile.puts("}

  public List toList() {
    return Lists.newArrayList(#{getArgs(1..i)});
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if(o == null || getClass() != o.getClass()) {
      return false;
    }
    #{tupleName} tuple = (#{tupleName}) o;
    return tuple.toList().equals(toList());
  }
  @Override
  public int hashCode() {
    int hash = 1;")
  (1..i).each do |j|
    newTupleFile.puts("    hash = 31 * hash + (k#{j} != null ? k#{j}.hashCode() : 0);")
  end
  newTupleFile.puts("    return hash;
  }
")
  (1..i).each do |j|
    newTupleFile.puts("  public K#{j} getK#{j}() {
    return k#{j};
  }")
  end
  if i > 2 then newTupleFile.puts("
  public #{lastTupleName}<#{getGenerics(1..i-1)}> init() {
    return new #{lastTupleName}<#{getGenerics(1..i-1)}>(#{getArgs(1..i-1)});
  }
  public K1 head() {
    return k1;
  }
  public K#{i} last() {
    return k#{i};
  }
  public #{lastTupleName}<#{getGenerics(2..i)}> tail() {
    return new #{lastTupleName}<#{getGenerics(2..i)}>(#{getArgs(2..i)});
  }
}")
  else newTupleFile.puts('
  public K1 head() {
    return k1;
  }
  public K1 init() {
    return k1;
  }

  public K2 last() {
    return k2;
  }
  public K2 tail() {
    return k2;
  }

}')
  end
end


