(ns svg.core
  (:use-macros [svg.macros :only [defsvg]]))

(def ^:private svg-ns
  "http://www.w3.org/2000/svg")

(defn- make-svg
  "Creates an SVG element of type `tag` with attributes specified by `attrs`."
  [tag attrs]
  (let [elem (.createElementNS js/document svg-ns (name tag))]
    (loop [attrs attrs]
      (if-let [[k v] (first attrs)]
        (do (.setAttribute elem (name k) v)
            (recur (rest attrs)))
        elem))))

(defn- point->string
  "Returns a string representation of the point `[x y]`, suitable for inclusion
   in many SVG attribute values."
  [[x y]]
  (str x "," y))

(defn- points->string
  "Returns a string representation of `points`, suitable for usage as the value
   of the `points` attribute of any SVG element that has one."
  [points]
  (->> points
    (map point->string)
    (interpose " ")
    (apply str)))

(defsvg circle [[x y] r]
  {:cx x :cy y :r r})

(defsvg ellipse [[x y] rx ry]
  {:cx x :cy y :rx rx :ry ry})

(defsvg line [[x1 y1] [x2 y2]]
  {:x1 x1 :y1 y1 :x2 x2 :y2 y2})

(defsvg polygon [points]
  {:points (points->string points)})

(defsvg polyline [points]
  {:points (points->string points)})

(defsvg rect [[x y] width height]
  {:x x :y y :width width :height height})
