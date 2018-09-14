using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Neomer.EveryPrice.SDK.Models
{
    public class Shop : BaseEntity, IShop
    {

        public virtual string Name { get; set; }

        public virtual string Address { get; set; }

        public virtual DateTime CreationDate { get; set; }

        public virtual IGroup Group { get; set; }

        public virtual double Lat { get; set; }

        public virtual double Lng { get; set; }

    }
}
