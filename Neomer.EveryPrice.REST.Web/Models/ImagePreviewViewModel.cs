using Neomer.EveryPrice.SDK.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Neomer.EveryPrice.REST.Web.Models
{
    public class ImagePreviewViewModel
    {

        public ImagePreviewViewModel()
        {

        }

        public ImagePreviewViewModel(IBlob image)
        {
            if (image == null)
            {
                return;
            }

            Uid = image.Uid;
            CreationDate = image.CreationDate;
        }

        public Guid Uid;

        public DateTime CreationDate;

    }
}