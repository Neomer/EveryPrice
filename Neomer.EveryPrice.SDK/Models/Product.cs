﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Neomer.EveryPrice.SDK.Models
{
    public class Product : BaseEntity, IProduct
    {

        public virtual string Name { get; set; }

        public virtual DateTime? CreationDate { get; set; }

        public virtual IShop Shop { get; set; }

        public virtual IGroup Group { get; set; }

        public virtual IUser Creator { get; set; }

        public virtual IList<IPrice> Prices { get; set; }

        public virtual IList<IBlob> Images { get; set; }
    }
}
