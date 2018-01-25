/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.inacap.mascoteria2018.tags;

import java.util.Base64;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author alumnossur
 */
public class TagImage extends SimpleTagSupport {

    private Object array;
    private int tam;

    /**
     * Called by the container to invoke this tag. The implementation of this
     * method is provided by the tag library developer, and handles all tag
     * processing, body iteration, etc.
     */
    @Override
    public void doTag() throws JspException {
        JspWriter out = getJspContext().getOut();

        try {
            // TODO: insert code to write html before writing the body content.
            // e.g.:
            //
            // out.println("<strong>" + attribute_1 + "</strong>");
            // out.println("    <blockquote>");

            JspFragment f = getJspBody();
            if (f != null) {
                f.invoke(out);
            }

            // TODO: insert code to write html after writing the body content.
            // e.g.:
            //
            // out.println("    </blockquote>");
            byte[] data = (byte[]) this.array;
            out.print("<img class='materialboxed' width='" + this.tam + "' src='data:image/*;base64," + Base64.getEncoder().encodeToString(data) + "'/>");

        } catch (java.io.IOException ex) {
            throw new JspException("Error in TagImage tag", ex);
        }
    }

    public void setArray(Object array) {
        this.array = array;
    }

    public void setTam(int tam) {
        this.tam = tam;
    }

}
