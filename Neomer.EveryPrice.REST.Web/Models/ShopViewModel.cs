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
            Uid = shop.Uid;
            Name = shop.Name;
            Address = shop.Address;
            CreationDate = shop.CreationDate;
            Lat = shop.Lat;
            Lng = shop.Lng;
            Creator = shop.Creator.Username;
            Tags = shop.Tags == null ? null :
                shop.Tags
                .Select(_ => new TagsViewModel(_))
                .ToList<TagsViewModel>();
        }


        public Guid Uid;

        public string Name;

        public string Address;

        public DateTime CreationDate;

        public double Lat;

        public double Lng;

        public string Creator;

        public IList<TagsViewModel> Tags;

    }
}