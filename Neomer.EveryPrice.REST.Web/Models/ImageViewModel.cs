using Neomer.EveryPrice.SDK.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Neomer.EveryPrice.REST.Web.Models
{
    public class ImageViewModel : ImagePreviewViewModel
    {

        public ImageViewModel()
        {

        }

        public ImageViewModel(IBlob image) : base(image)
        {
            EncriptedData = Convert.ToBase64String(image.Data);
        }

        public String EncriptedData;

    }
}