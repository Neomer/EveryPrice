using Neomer.EveryPrice.SDK.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Neomer.EveryPrice.REST.Web.Models
{

    [Serializable]
    public class ShopViewModel
    {
        public ShopViewModel()
        {

        }

        public ShopViewModel(IShop shop)
        {
            if (shop == null)
            {
                return;
            }

            Uid = shop.Uid;
            Name = shop.Name;
            Address = shop.Address;
            CreationDate = shop.CreationDate;
            Lat = shop.Lat;
            Lng = shop.Lng;
            Creator = new UserViewModel(shop.Creator);
            Tags = shop.Tags == null ? null :
                shop.Tags
                    .Select(_ => new TagsViewModel(_))
                    .ToList<TagsViewModel>();
            Images = shop.Images == null ? null :
                shop.Images
                    .Select(_ => new ImagePreviewViewModel(_))
                    .ToList<ImagePreviewViewModel>();
        }


        public Guid Uid;

        public string Name;

        public string Address;

        public DateTime CreationDate;

        public double Lat;

        public double Lng;

        public UserViewModel Creator;

        public IList<TagsViewModel> Tags;

        public IList<ImagePreviewViewModel> Images;

    }
}