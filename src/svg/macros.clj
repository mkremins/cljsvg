(ns svg.macros)

(defmacro defsvg
  "Defines a function with name `tagname`, signature `args`, and body `body`
   that, when called, returns a map representing the unique attributes of a
   newly created SVG element with tag type `tagname`."
  [tagname args & body]
  (let [attrs (gensym)
        fullargs (conj args attrs)]
    `(defn ~tagname
      (~args (~tagname ~@args {}))
      (~fullargs
        (svg.core/make-svg ~(keyword tagname) (merge ~attrs ~@body))))))
