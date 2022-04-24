package me.mitul.aij.Constants;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

import java.util.ArrayList;
import java.util.List;

public class JustifiedTextView extends View {
    private boolean hasTextBeenDrown = false;
    private Context mContext;
    private TextPaint textPaint;
    private int lineSpace = 0;
    private int lineHeight = 0;
    private int textAreaWidth = 0;
    private int measuredViewHeight = 0;
    private int measuredViewWidth = 0;
    private String text;
    private List<String> lineList = new ArrayList<>();

    public JustifiedTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        constructor(context, attrs);
    }

    public JustifiedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        constructor(context, attrs);
    }

    public JustifiedTextView(Context context) {
        super(context);
        constructor(context, null);
    }

    private void constructor(Context context, AttributeSet attrs) {
        mContext = context;
        XmlToClassAttribHandler mXmlParser = new XmlToClassAttribHandler(mContext, attrs);
        initTextPaint();

        if (attrs != null) {
            String text;
            int textColor;
            int textSize;
            int textSizeUnit;
            text = mXmlParser.getTextValue();
            textColor = mXmlParser.getColorValue();
            textSize = mXmlParser.getTextSize();
            textSizeUnit = mXmlParser.gettextSizeUnit();
            setText(text);
            setTextColor(textColor);
            if (textSizeUnit == -1) setTextSize(textSize);
            else setTextSize(textSizeUnit, textSize);
//			setText(XmlToClassAttribHandler.GetAttributeStringValue(mContext, attrs, namespace, key, ""));
            setTypeFace(mXmlParser.getTypeFace());
            //setTypeFace(Typeface.create("serif", Typeface.NORMAL));
        }

        ViewTreeObserver observer = getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (hasTextBeenDrown) return;
                hasTextBeenDrown = true;
                setTextAreaWidth(getWidth() - (getPaddingLeft() + getPaddingRight()));
                calculate();
            }
        });
    }

    private void calculate() {
        setLineHeight(getTextPaint());
        lineList.clear();
        lineList = divideOriginalTextToStringLineList(getText());
        setMeasuredDimentions(lineList.size(), getLineHeight(), getLineSpace());
        measure(getMeasuredViewWidth(), getMeasuredViewHeight());
    }

    private void initTextPaint() {
        textPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        textPaint.setTextAlign(Align.LEFT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getMeasuredViewWidth() > 0) {
            requestLayout();
            setMeasuredDimension(getMeasuredViewWidth(), getMeasuredViewHeight());
        } else super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int rowIndex = getPaddingTop();
        int colIndex = 0;
        if (getAlignment() == Align.RIGHT) colIndex = getPaddingLeft() + getTextAreaWidth();
        else colIndex = getPaddingLeft();
        for (int i = 0; i < lineList.size(); i++) {
            rowIndex += getLineHeight() + getLineSpace();
            canvas.drawText(lineList.get(i), colIndex, rowIndex, getTextPaint());
        }
    }

    private List<String> divideOriginalTextToStringLineList(String originalText) {
        ArrayList<String> listStringLine = new ArrayList<String>();
        String line = "";
        float textWidth;
        String[] listParageraphes = originalText.split("\n");

        for (String listParageraphe : listParageraphes) {
            String[] arrayWords = listParageraphe.split(" ");
            for (int i = 0; i < arrayWords.length; i++) {
                line += arrayWords[i] + " ";
                textWidth = getTextPaint().measureText(line);
                if (getTextAreaWidth() == textWidth) {
                    listStringLine.add(line);
                    line = "";//make line clear
                    continue;
                } else if (getTextAreaWidth() < textWidth) {
                    int lastWordCount = arrayWords[i].length();
                    line = line.substring(0, line.length() - lastWordCount - 1);
                    if (line.trim().length() == 0)
                        continue;
                    line = justifyTextLine(textPaint, line.trim(), getTextAreaWidth());
                    listStringLine.add(line);
                    line = "";
                    i++;
                    continue;
                }

                if (i == arrayWords.length - 1) {
                    listStringLine.add(line);
                    line = "";
                }
            }
        }
        return listStringLine;
    }

    private String justifyTextLine(TextPaint textPaint, String lineString, int textAreaWidth) {
        int gapIndex = 0;
        float lineWidth = textPaint.measureText(lineString);
        while (lineWidth < textAreaWidth && lineWidth > 0) {
            gapIndex = lineString.indexOf(" ", gapIndex + 2);
            if (gapIndex == -1) {
                gapIndex = 0;
                gapIndex = lineString.indexOf(" ", gapIndex + 1);
                if (gapIndex == -1) return lineString;
            }
            lineString = lineString.substring(0, gapIndex) + "  " + lineString.substring(gapIndex + 1);
            lineWidth = textPaint.measureText(lineString);
        }
        return lineString;
    }

    private void setLineHeight(TextPaint textPaint) {
        Rect bounds = new Rect();
        /*"این حسین کیست که عالم همه دیوانه اوست"*/
        String sampleStr = "Hussein, who is the crazy world of all";
        textPaint.getTextBounds(sampleStr, 0, sampleStr.length(), bounds);
        setLineHeight(bounds.height());
    }

    public void setMeasuredDimentions(int lineListSize, int lineHeigth, int lineSpace) {
        int mHeight = lineListSize * (lineHeigth + lineSpace) + lineSpace;
        mHeight += getPaddingRight() + getPaddingLeft();
        setMeasuredViewHeight(mHeight);
        setMeasuredViewWidth(getWidth());
    }

    private int getTextAreaWidth() {
        return textAreaWidth;
    }

    private void setTextAreaWidth(int textAreaWidth) {
        this.textAreaWidth = textAreaWidth;
    }

    private int getLineHeight() {
        return lineHeight;
    }

    private void setLineHeight(int lineHeight) {
        this.lineHeight = lineHeight;
    }

    private int getMeasuredViewHeight() {
        return measuredViewHeight;
    }

    private void setMeasuredViewHeight(int measuredViewHeight) {
        this.measuredViewHeight = measuredViewHeight;
    }

    private int getMeasuredViewWidth() {
        return measuredViewWidth;
    }

    private void setMeasuredViewWidth(int measuredViewWidth) {
        this.measuredViewWidth = measuredViewWidth;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        calculate();
        invalidate();
    }

    public void setText(int resid) {
        setText(mContext.getResources().getString(resid));
    }

    public Typeface getTypeFace() {
        return getTextPaint().getTypeface();
    }

    public void setTypeFace(Typeface typeFace) {
        getTextPaint().setTypeface(typeFace);
    }

    public float getTextSize() {
        return getTextPaint().getTextSize();
    }

    private void setTextSize(float textSize) {
        getTextPaint().setTextSize(textSize);
        calculate();
        invalidate();
    }

    public void setTextSize(int unit, float textSize) {
        textSize = TypedValue.applyDimension(unit, textSize, mContext.getResources().getDisplayMetrics());
        setTextSize(textSize);
    }

    public TextPaint getTextPaint() {
        return textPaint;
    }

    public void setTextPaint(TextPaint textPaint) {
        this.textPaint = textPaint;
    }

    public void setLineSpacing(int lineSpace) {
        this.lineSpace = lineSpace;
        invalidate();
    }

    public int getTextColor() {
        return getTextPaint().getColor();
    }

    public void setTextColor(int textColor) {
        getTextPaint().setColor(textColor);
        invalidate();
    }

    public int getLineSpace() {
        return lineSpace;
    }

    public Align getAlignment() {
        return getTextPaint().getTextAlign();
    }

    public void setAlignment(Align align) {
        getTextPaint().setTextAlign(align);
        invalidate();
    }
}
