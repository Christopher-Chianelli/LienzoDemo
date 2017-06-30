package com.testApp.client;

import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.client.core.shape.Text;
import com.ait.lienzo.client.core.shape.wires.IControlHandle;
import com.ait.lienzo.client.core.shape.wires.IControlHandleList;
import com.ait.lienzo.client.core.shape.wires.LayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.ait.lienzo.client.core.shape.wires.event.WiresResizeEndEvent;
import com.ait.lienzo.client.core.shape.wires.event.WiresResizeEndHandler;
import com.ait.lienzo.client.core.shape.wires.event.WiresResizeStartEvent;
import com.ait.lienzo.client.core.shape.wires.event.WiresResizeStartHandler;
import com.ait.lienzo.client.core.shape.wires.event.WiresResizeStepEvent;
import com.ait.lienzo.client.core.shape.wires.event.WiresResizeStepHandler;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.lienzo.client.widget.LienzoPanel;
import com.ait.lienzo.shared.core.types.TextAlign;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.sun.xml.internal.ws.server.sei.SEIInvokerTube;

public class TestApp implements EntryPoint{

    public void onModuleLoad(){
        RectangleWidget panel = new RectangleWidget(1000,1000);
        RootPanel.get().add(panel);
    }

    private static class RectangleWidget extends LienzoPanel
    {
        private WiresManager m_manager;
        private final LayoutContainer.Layout m_layout = LayoutContainer.Layout.CENTER;
        private final TextAlign align = TextAlign.CENTER;
        Text text;
        WiresShape shape;

        public RectangleWidget(int width, int height) {
            super(width, height);
            Layer layer = new Layer();
            m_manager = WiresManager.get(layer);
            add(layer);
            setBackgroundLayer(new Layer());
            init();
        }

        private void init() {
            final int strokeWidth = 1;
            final Layer layer = new Layer();
            create(m_layout,
                             ColorName.ALICEBLUE.getColorString());
            add(layer);
        }

        private WiresShape create(LayoutContainer.Layout layout, String color)
        {
            text = new Text("").setText("A very long string that should wrap eventally").
                    setFillColor(ColorName.BLACK.getColor()).
            setFontSize(30).
                    setFontFamily("Georgia").
            setTextAlign(align);
            final MultiPath path = new MultiPath().rect(0, 0, getWidth()/4, getHeight()/4)
                    .setStrokeWidth(1)
                    .setStrokeColor(color)
                    .setFillColor(ColorName.LIGHTGREY);
            shape =
                    new WiresShape(path)
                            .setX(0)
                            .setY(0)
                            .setDraggable(true)
                            .addChild(text, layout);

            text.setWrapBoundaries(path.getBoundingBox());
            m_manager.register(shape);
            m_manager.getMagnetManager().createMagnets(shape);
            m_manager.getLayer().add(shape);
            addResizeHandlers(shape);
            shape.refresh();

            return shape;
        }

        private void addResizeHandlers(final WiresShape shape)
        {
            shape
                    .setResizable(true)
                    .getPath()
                    .addNodeMouseClickHandler(new NodeMouseClickHandler()
                    {
                        @Override
                        public void onNodeMouseClick(NodeMouseClickEvent event)
                        {
                            final IControlHandleList controlHandles = shape.loadControls(IControlHandle.ControlHandleStandardType.RESIZE);
                            if (null != controlHandles)
                            {
                                if (event.isShiftKeyDown())
                                {
                                    controlHandles.show();
                                } else
                                {
                                    controlHandles.hide();
                                }
                            }

                        }
                    });

            shape.addWiresResizeStartHandler(new WiresResizeStartHandler()
            {
                @Override
                public void onShapeResizeStart(final WiresResizeStartEvent event)
                {
                    onShapeResize(event.getWidth(), event.getHeight());
                }
            });

            shape.addWiresResizeStepHandler(new WiresResizeStepHandler()
            {
                @Override
                public void onShapeResizeStep(final WiresResizeStepEvent event)
                {
                    onShapeResize(event.getWidth(), event.getHeight());

                }
            });

            shape.addWiresResizeEndHandler(new WiresResizeEndHandler()
            {
                @Override
                public void onShapeResizeEnd(final WiresResizeEndEvent event)
                {
                    onShapeResize(event.getWidth(), event.getHeight());
                }
            });
        }

        private void onShapeResize(final double width, final double height)
        {
            shape.removeChild(text);
            text.setWrapBoundaries(new BoundingBox(0,0,width,height));
            shape.addChild(text,m_layout);
        }
    }

}