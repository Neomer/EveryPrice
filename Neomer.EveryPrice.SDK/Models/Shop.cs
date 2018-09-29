using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Neomer.EveryPrice.SDK.Models
{
    public class Shop : BaseEntity, IShop
    {
        public Shop()
        {

        }

        public Shop(IShop shop)
        {
            Name = shop.Name;
            Address = shop.Address;
            CreationDate = shop.CreationDate;
            Group = shop.Group;
            Lat = shop.Lat;
            Lng = shop.Lng;
            Creator = shop.Creator;
            Tags = shop.Tags;
            Images = shop.Images;
        }


        public virtual string Name { get; set; }

        public virtual string Address { get; set; }

        public virtual DateTime CreationDate { get; set; }

        public virtual IGroup Group { get; set; }

        public virtual double Lat { get; set; }

        public virtual double Lng { get; set; }

        public virtual IUser Creator { get; set; }

        public virtual IList<ITag> Tags { get; set; }

        public virtual IList<IBlob> Images { get; set; }
    }
}
