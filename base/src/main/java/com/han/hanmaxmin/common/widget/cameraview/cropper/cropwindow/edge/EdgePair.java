package com.han.hanmaxmin.common.widget.cameraview.cropper.cropwindow.edge;

/*
 * @Author yinzh
 * @Date   2018/6/20 16:49
 * @Description
 */
public class EdgePair {

    // Member Variables ////////////////////////////////////////////////////////

    public Edge primary;
    public Edge secondary;

    // Constructor /////////////////////////////////////////////////////////////

    public EdgePair(Edge edge1, Edge edge2) {
        primary = edge1;
        secondary = edge2;
    }

}
