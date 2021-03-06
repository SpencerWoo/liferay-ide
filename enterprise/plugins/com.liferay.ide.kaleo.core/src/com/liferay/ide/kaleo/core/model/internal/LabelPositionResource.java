/**
 * Copyright (c) 2014 Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the End User License
 * Agreement for Liferay IDE ("License"). You may not use this file
 * except in compliance with the License. You can obtain a copy of the License
 * by contacting Liferay, Inc. See the License for the specific language
 * governing permissions and limitations under the License, including but not
 * limited to distribution rights of the Software.
 */

package com.liferay.ide.kaleo.core.model.internal;

import com.liferay.ide.kaleo.core.model.Position;

import org.eclipse.sapphire.Property;
import org.eclipse.sapphire.PropertyBinding;
import org.eclipse.sapphire.PropertyDef;
import org.eclipse.sapphire.Resource;
import org.eclipse.sapphire.ValuePropertyBinding;

/**
 * @author Gregory Amerson
 */
public class LabelPositionResource extends Resource
{
    private Point point;

    public LabelPositionResource( Point point, Resource parent )
    {
        super( parent );
        this.point = point;
    }

    public Point getPoint()
    {
        return this.point;
    }

    @Override
    protected PropertyBinding createBinding( final Property property )
    {
        PropertyBinding binding = null;
        final PropertyDef def = property.definition();

        if( Position.PROP_X.equals( def ) || Position.PROP_Y.equals( def ) )
        {
            binding = new ValuePropertyBinding()
            {
                @Override
                public String read()
                {
                    String retval = null;

                    if (LabelPositionResource.this.point != null)
                    {
                        if (Position.PROP_X.equals( def ) )
                        {
                            retval = Integer.toString( LabelPositionResource.this.point.getX() );
                        }
                        else if (Position.PROP_Y.equals( def ) )
                        {
                            retval = Integer.toString( LabelPositionResource.this.point.getY());
                        }
                    }

                    return retval;
                }

                @Override
                public void write( String value )
                {
                    if (LabelPositionResource.this.point == null)
                    {
                        LabelPositionResource.this.point = new Point();
                    }

                    if (Position.PROP_X.equals( def ) )
                    {
                        LabelPositionResource.this.point.setX( Integer.parseInt( value ) );
                    }
                    else if (Position.PROP_Y.equals( def ) )
                    {
                        LabelPositionResource.this.point.setY( Integer.parseInt( value ));
                    }

//                    if (Position.PROP_X.equals( def ) || Position.PROP_Y.equals( def ))
//                    {
//                        WorkflowNodeMetadataObject parentMetadata = parent().adapt( WorkflowNodeMetadataResource.class ).getMetadata();
//                        parentMetadata.setNodeLocation( LabelPositionResource.this.point );
//                    }

                    parent().adapt( WorkflowNodeMetadataResource.class ).saveMetadata();
                }
            };
        }

        if( binding != null )
        {
            binding.init( property );
        }

        return binding;
    }

}
