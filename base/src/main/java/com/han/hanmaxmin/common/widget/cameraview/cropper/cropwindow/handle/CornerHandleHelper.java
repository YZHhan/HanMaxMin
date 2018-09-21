package com.han.hanmaxmin.common.widget.cameraview.cropper.cropwindow.handle;

import android.graphics.Rect;

import com.han.hanmaxmin.common.widget.cameraview.cropper.cropwindow.edge.Edge;
import com.han.hanmaxmin.common.widget.cameraview.cropper.cropwindow.edge.EdgePair;

/*
 * @Author yinzh
 * @Date   2018/6/20 16:53
 * @Description:HandleHelper class to handle corner Handles (i.e. top-left, top-right,bottom-left, and bottom-right handles).
 */
public class CornerHandleHelper extends HandleHelper{
    // Constructor /////////////////////////////////////////////////////////////

    CornerHandleHelper(Edge horizontalEdge, Edge verticalEdge) {
        super(horizontalEdge, verticalEdge);
    }

    // HandleHelper Methods ////////////////////////////////////////////////////

    @Override
    void updateCropWindow(float x,
                          float y,
                          float targetAspectRatio,
                          Rect imageRect,
                          float snapRadius) {

        final EdgePair activeEdges = getActiveEdges(x, y, targetAspectRatio);
        final Edge primaryEdge = activeEdges.primary;
        final Edge secondaryEdge = activeEdges.secondary;

        primaryEdge.adjustCoordinate(x, y, imageRect, snapRadius, targetAspectRatio);
        secondaryEdge.adjustCoordinate(targetAspectRatio);

        if (secondaryEdge.isOutsideMargin(imageRect, snapRadius)) {
            secondaryEdge.snapToRect(imageRect);
            primaryEdge.adjustCoordinate(targetAspectRatio);
        }
    }
}
