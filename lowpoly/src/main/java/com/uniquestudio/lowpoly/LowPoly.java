package com.uniquestudio.lowpoly;


import ohos.agp.render.Canvas;
import ohos.agp.render.Paint;
import ohos.agp.render.Path;
import ohos.agp.render.Texture;
import ohos.agp.utils.Color;
import ohos.media.image.PixelMap;
import ohos.media.image.common.PixelFormat;
import ohos.media.image.common.Position;
import ohos.media.image.common.Rect;
import ohos.media.image.common.Size;

/**
 * Created by CoXier on 2016/9/14.
 */
public class LowPoly {
    static {
        System.loadLibrary("lowpoly");
    }

    public static native int[] getTriangles(int[] pixels, int width, int height, int pointNum);


    /**
     * @param input
     * @param gradientThresh
     * @return low poly bitmap
     */
    public static PixelMap generate(PixelMap input, int gradientThresh) {
        int width = input.getImageInfo().size.width;
        int height = input.getImageInfo().size.height;

        PixelMap.InitializationOptions options = new PixelMap.InitializationOptions();
        options.size = new Size(width, height);
        options.pixelFormat = PixelFormat.ARGB_8888;
        PixelMap newImage = PixelMap.create(options);
        Canvas canvas = new Canvas(new Texture(newImage));
        Paint paint = new Paint();
        paint.setAntiAlias(false);
        paint.setStyle(Paint.Style.FILL_STYLE);
        int x1, x2, x3, y1, y2, y3;
        int pixels[] = new int[width * height];
        input.readPixels(pixels,0,width,new Rect(0,0,width,height));
        int[] triangles = getTriangles(pixels, width, height,gradientThresh);
        for (int i = 0; i < triangles.length; i = i + 6) {
            x1 = triangles[i];
            y1 = triangles[i + 1];
            x2 = triangles[i + 2];
            y2 = triangles[i + 3];
            x3 = triangles[i + 4];
            y3 = triangles[i + 5];

            int color = input.readPixel(new Position((x1 + x2 + x3) / 3, (y1 + y2 + y3) / 3));
            Path path = new Path();
            path.moveTo(x1, y1);
            path.lineTo(x2, y2);
            path.lineTo(x3, y3);
            path.close();
            paint.setColor(new Color(color));
            canvas.drawPath(path, paint);
        }
        return newImage;
    }
}
